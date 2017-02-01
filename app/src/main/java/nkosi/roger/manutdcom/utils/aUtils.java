package nkosi.roger.manutdcom.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import java.security.PublicKey;

import nkosi.roger.manutdcom.R;

/**
 * Created by ROGER on 12/26/2016.
 */
public class aUtils {


    public static void invokeShare(Context context, String title, String body) {
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, body + "\n#mufc");

        context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_title)));
    }

    public static void showDialog(final Context context){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Failed, please try again.");
        builder1.setCancelable(false);
        builder1.setIcon(R.drawable.ic_warning_24dp);

        builder1.setPositiveButton(
                "Try again",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public static void showAlertDialog(Context context, String title, String content){
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        if (!content.equals("")){
            builder.setMessage(content);
        }
        builder.show();
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

}
