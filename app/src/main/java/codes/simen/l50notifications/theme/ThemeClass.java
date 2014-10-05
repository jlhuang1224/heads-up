/*
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package codes.simen.l50notifications.theme;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import codes.simen.l50notifications.R;
import codes.simen.l50notifications.util.Mlog;
import codes.simen.l50notifications.util.RoundDrawable;

/**
 * Extend this class and override any methods you need to modify.
 */
public class ThemeClass {

    public ThemeClass (ViewStub stub) {
        stub.setLayoutResource(R.layout.activity_read_inner);
    }

    public ThemeClass () {

    }

    /*
     Load layout resource here. Also, load any theme settings
     */
    public void init (LinearLayout layout) {

    }

    /*
     Fetch the root view of the theme
     */
    public ViewGroup getRootView (LinearLayout layout) {
        return (ViewGroup) layout.findViewById(R.id.linearLayout);
    }

    /*
     Fetch a reference to the action button area
     */
    public ViewGroup getActionButtons(LinearLayout layout) {
        return (ViewGroup) layout.findViewById(R.id.action_buttons);
    }

    /*
     Remove all action buttons from the layout, in case the layout needs to be re-used.
    */
    public void removeActionButtons (ViewGroup actionButtonViewGroup) {
        while (actionButtonViewGroup.getChildCount() > 0) {
            actionButtonViewGroup.removeViewAt(0);
        }
    }

    /*
     This notification does have action buttons. Display the action button area. If count parameter is -1, only display the action button view
     */
    public void showActionButtons(LinearLayout layout, int count) {
        layout.findViewById(R.id.button_container).setVisibility(View.VISIBLE);
    }

    /*
     This notification doesn't have any action buttons. Hide the action button area.
     */
    public void hideActionButtons(LinearLayout layout) {
        layout.findViewById(R.id.button_container).setVisibility(View.GONE);
    }

    /*
     Add an action button to the layout.
     */
    public void addActionButton(ViewGroup actionButtons, String actionTitle, Drawable icon, View.OnClickListener clickListener, float fontMultiplier) {
        LayoutInflater inflater = LayoutInflater.from(actionButtons.getContext());
        ViewGroup v = (ViewGroup) inflater.inflate(
                R.layout.button_notification, actionButtons);

        Button button = (Button) v.getChildAt(v.getChildCount() - 1);
        button.setText(actionTitle);
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontMultiplier * button.getTextSize());
        if (icon != null) {
            icon.mutate().setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);
            button.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
        }
        button.setOnClickListener(clickListener);
    }

    /*
     Return the view displaying the notification icon.
     */
    public ImageView getIconView(LinearLayout layout) {
        return (ImageView) layout.findViewById(R.id.notification_icon);
    }

    /*
     Return the view displaying the small notification icon.
     Should return null if the theme doesn't use small icons.
     */
    public ImageView getSmallIconView(LinearLayout layout) {
        return (ImageView) layout.findViewById(R.id.notification_icon_small);
    }

    /*
     Set the notification icon from a bitmap.
     */
    public void setIcon(ImageView imageView, Bitmap bitmap, boolean round_icons) {
        if (bitmap == null) return;
        if (round_icons) {
            final double minimumWidthForRoundIcon = imageView.getContext().getResources().
                    getDimension(R.dimen.notification_ic_size) / (2 * Math.cos(Math.toRadians(45)));
            int bitmapWidth = bitmap.getWidth();
            Mlog.v(bitmapWidth, minimumWidthForRoundIcon);

            if (bitmapWidth >= minimumWidthForRoundIcon) {
                try {
                    RoundDrawable roundedDrawable = new RoundDrawable(bitmap);
                    imageView.setImageDrawable(roundedDrawable);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                } catch (Exception e) {
                    e.printStackTrace();
                    imageView.setImageBitmap(bitmap);
                }
            } else {
                imageView.setImageBitmap(bitmap);
            }
            imageView.setBackgroundResource(R.drawable.circle);
        } else
            imageView.setImageBitmap(bitmap);
    }

    /*
     Set the small notification icon.
     */
    public void setSmallIcon(ImageView smallIcon, Drawable drawable) {
        if (drawable != null) smallIcon.setImageDrawable(drawable);
        else                  smallIcon.setVisibility(View.GONE);
    }

    /*
     Fetch the dismiss button.
     */
    public View getDismissButton(LinearLayout layout) {
        return layout.findViewById(R.id.notification_dismiss);
    }


    /*
     Hide the dismiss button.
     */
    public void hideDismissButton(View dismissButton) {
        dismissButton.setVisibility(View.GONE);
    }

    /*
     In case you need to do something when stopping. Called after the view is removed from the window manager.
     */
    public void destroy(LinearLayout layout) {
    }
}