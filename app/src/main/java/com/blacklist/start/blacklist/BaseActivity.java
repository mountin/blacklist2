package com.blacklist.start.blacklist;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by mountin on 11.08.2015.
 */
public class BaseActivity extends ActionBarActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == 16908332){
            finish();
        }

        if (id == R.id.action_settings) {

            return true;
        }

        if (id == R.id.callList) {
            Intent intent = new Intent(this, LogListActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.language) {
            Intent intent = new Intent(this, LocalisationActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_compose) {
            Intent intent = new Intent(this, AddNumberActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.contact_us) {
            Intent browseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.jaqqa.com"));
            startActivity(browseIntent);
            return true;
        }
        if (id == R.id.feedback) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback: " + getDeviceName());
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Dear developers ... My device is:" + getDeviceName());
            shareIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@jaqqa.com"});
            startActivity(Intent.createChooser(shareIntent, "Recomendation"));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            model = model;

        } else {
            model += manufacturer;
        }
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return model +" Android:"+ Build.VERSION.RELEASE+" package v"+pInfo.versionName ;

    }
}
