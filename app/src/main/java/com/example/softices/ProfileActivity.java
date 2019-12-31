package com.example.softices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.exifinterface.media.ExifInterface;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class ProfileActivity extends AppCompatActivity {
    private final static int ALL_PERMISSIONS_RESULT = 107;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<String>();
    private ArrayList<String> permissions = new ArrayList<>();

    Bitmap myBitmap;
    Uri picUri;
    EditText emailEditText, firstEdittText, LastEdittText, addressedt, cityedt, pincodeedt;
    RadioButton rdofemale, rdomale;
    RadioGroup rdggender;
    Button btnupdate;
    CircleImageView croppedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
        final DatabaseHelper databaseHelper = new DatabaseHelper(this);
        final UserModel userModel = databaseHelper.getCurrentUser(RetriveEmailFromShared(this));

        firstEdittText.setText(userModel.getFirstname());
        LastEdittText.setText(userModel.getLastname());
        emailEditText.setText(userModel.getEmail());
        if (userModel.getGender().equalsIgnoreCase("male"))
            rdomale.setChecked(true);
        else if (userModel.getGender().equalsIgnoreCase("female"))
            rdofemale.setChecked(true);
        addressedt.setText(userModel.getAddress());
        cityedt.setText(userModel.getCity());
        pincodeedt.setText(userModel.getPincode());
        croppedImageView.setImageBitmap(getImage(userModel.getimage()));

        permissions.add(String.valueOf(CAMERA));
        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[0]), ALL_PERMISSIONS_RESULT);
        }

        croppedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(getPickImageChooserIntent(), 200);
            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstName = firstEdittText.getText().toString();
                String lastName = LastEdittText.getText().toString();
                int selectedId = rdggender.getCheckedRadioButtonId();
                RadioButton rdbbutton = findViewById(selectedId);
                String mygender = rdbbutton.getText().toString();
                String address = addressedt.getText().toString();
                String city = cityedt.getText().toString();
                String pincode = pincodeedt.getText().toString();

                if (firstName.length() == 0) {
                    firstEdittText.setError("First name is required!");
                } else if (lastName.length() == 0) {
                    LastEdittText.setError("Last name is required!");
                } else if (address.length() == 0) {
                    addressedt.setError("Address is required!");
                } else if (city.length() == 0) {
                    cityedt.setError("city required!");
                } else if (pincode.length() == 0) {
                    pincodeedt.setError("pincode is required!");
                } else {
                    userModel.setGender(mygender);
                    userModel.setFirstname(firstName);
                    userModel.setImage(getImageBytes(myBitmap));
                    userModel.setLastname(lastName);
                    userModel.setAddress(address);
                    userModel.setCity(city);
                    userModel.setPincode(pincode);
                    if (databaseHelper.updateUser(userModel)) {
                        Toast.makeText(getApplicationContext(), "succesfully update.....", Toast.LENGTH_SHORT).show();
                        finish();
                    } else
                        Toast.makeText(getApplicationContext(), "something want wrong...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static byte[] getImageBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    private void init() {
        firstEdittText = findViewById(R.id.edt_firstnamep);
        LastEdittText = findViewById(R.id.edt_lastname);
        emailEditText = findViewById(R.id.edt_email);
        rdofemale = findViewById(R.id.rdb_female);
        rdomale = findViewById(R.id.rdb_male);
        rdggender = findViewById(R.id.rdg_gender);
        addressedt = findViewById(R.id.edt_address);
        cityedt = findViewById(R.id.edt_city);
        pincodeedt = findViewById(R.id.edt_pincode);
        btnupdate = findViewById(R.id.btn_update);
        croppedImageView = findViewById(R.id.crcimg_profiles);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public static void saveToPreferences(Context context, String key, Boolean allowed) {
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putBoolean(key, allowed);
        prefsEditor.apply();
    }

    /**
     * Create a chooser intent to select the source to get image from.<br/>
     * The source can be camera's (ACTION_IMAGE_CAPTURE) or gallery's (ACTION_GET_CONTENT).<br/>
     * All possible sources are added to the intent chooser.
     */
    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public Intent getPickImageChooserIntent() {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        // collect all camera intents
        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        // collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        // the main intent is the last in the list (fucking android) so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (Objects.requireNonNull(intent.getComponent()).getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);
        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[0]));
        return chooserIntent;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_log_out) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * Create a chooser intent to select the source to get image from.<br />
     * The source can be camera's (ACTION_IMAGE_CAPTURE) or gallery's (ACTION_GET_CONTENT).<br />
     * All possible sources are added to the intent chooser.
     */
    /**
     * Get URI to image received from capture by camera.
     */
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap;
        Log.e("onActivityResult", requestCode + "," + resultCode);
        if (resultCode == Activity.RESULT_OK) {
            if (getPickImageResultUri(data) != null) {
                picUri = getPickImageResultUri(data);
                try {
                    myBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picUri);
                    croppedImageView.setImageBitmap(myBitmap);
                } catch (IOException e) {
                    Log.e("onAvtivityResult", e.toString());
                    e.printStackTrace();
                }
            } else {
                bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                myBitmap = bitmap;
                if (croppedImageView != null) {
                    croppedImageView.setImageBitmap(myBitmap);
                }
                croppedImageView.setImageBitmap(myBitmap);
            }
        }
    }

    /**
     * Get the URI of the selected image from {@link #getPickImageChooserIntent()}.<br />
     * Will return the correct URI for camera and gallery image.
     *
     * @param data the returned data of the activity result
     */
    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }


        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("pic_uri", picUri);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        picUri = savedInstanceState.getParcelable("pic_uri");
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }
        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage("These permissions are mandatory for the application. Please allow access.")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == ALL_PERMISSIONS_RESULT) {
            for (Object perms : permissionsToRequest) {
                if (hasPermission((String) perms)) {
                } else {
                    permissionsRejected.add((String) perms);
                }
            }
            if (permissionsRejected.size() > 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                        showMessageOKCancel(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Log.d("API123", "permisionrejected " + permissionsRejected.size());
                                requestPermissions(permissionsRejected.toArray(new String[0]), ALL_PERMISSIONS_RESULT);
                            }
                        });
                    }
                }
            }
        }
        saveToPreferences(ProfileActivity.this, ALLOW_KEY, true);
    }

    static String RetriveEmailFromShared(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("email", "");
    }
}
