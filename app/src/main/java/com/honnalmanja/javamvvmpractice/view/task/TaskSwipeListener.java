package com.honnalmanja.javamvvmpractice.view.task;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.honnalmanja.javamvvmpractice.R;
import com.honnalmanja.javamvvmpractice.utils.LogUtil;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

abstract public class TaskSwipeListener extends ItemTouchHelper.Callback {

    private static final String TAG = TaskSwipeListener.class.getSimpleName();

    Context mContext;
    private Paint mClearPaint;
    private ColorDrawable mBackground;
    private int backgroundRedColor = Color.parseColor("#b80f0a");
    private int backgroundGreenColor = Color.parseColor("#229954");
    private Drawable deleteDrawable, editDrawable;
    private int intrinsicWidth;
    private int intrinsicHeight;


    public TaskSwipeListener(Context context) {
        mContext = context;
        mBackground = new ColorDrawable();
        mClearPaint = new Paint();
        mClearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        deleteDrawable = ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_close_24);
        editDrawable = ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_edit_24);

    }

    @Override
    // ItemTouchHelper.LEFT = left swipe, ItemTouchHelper.RIGHT = right swipe
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    }

    @Override
    // use this for drag drop else return false
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;
        int itemHeight = itemView.getHeight();

        boolean isCancelled = dX == 0 && !isCurrentlyActive;

        if (isCancelled) {
            clearCanvas(c, itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            return;
        }

        if(dX > 0){ // swiping right
            intrinsicWidth = editDrawable.getIntrinsicWidth();
            intrinsicHeight = editDrawable.getIntrinsicHeight();
            mBackground.setColor(backgroundGreenColor);
            mBackground.setBounds(
                    itemView.getLeft(),
                    itemView.getTop(),
                    itemView.getLeft() + ((int) dX),
                    itemView.getBottom()
            );
            mBackground.draw(c);

            // get bounds where i need to place the icon
            // values the get the location of icon on screen based on itemView dimension
            int editIconTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
            int editIconMargin = (itemHeight - intrinsicHeight) / 2;
            int editIconLeft = itemView.getLeft() + editIconMargin;
            int editIconRight = itemView.getLeft() + editIconMargin + intrinsicWidth;
            int editIconBottom = editIconTop + intrinsicHeight;

            editDrawable.setBounds(editIconLeft, editIconTop, editIconRight, editIconBottom);
            editDrawable.draw(c);
        } else if (dX < 0){ // swiping left
            intrinsicWidth = deleteDrawable.getIntrinsicWidth();
            intrinsicHeight = deleteDrawable.getIntrinsicHeight();
            mBackground.setColor(backgroundRedColor);
            mBackground.setBounds(
                    itemView.getRight() + ((int) dX),
                    itemView.getTop(),
                    itemView.getRight(),
                    itemView.getBottom()
            );
            mBackground.draw(c);

            // get bounds where i need to place the icon
            // values the get the location of icon on screen based on itemView dimension
            int deleteIconTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
            int deleteIconMargin = (itemHeight - intrinsicHeight) / 2;
            int deleteIconLeft = itemView.getRight() - deleteIconMargin - intrinsicWidth;
            int deleteIconRight = itemView.getRight() - deleteIconMargin;
            int deleteIconBottom = deleteIconTop + intrinsicHeight;
            LogUtil.i(TAG, "---------------------------------------------------");
            LogUtil.d(TAG, "deleteIconTop: "+deleteIconTop);
            LogUtil.d(TAG, "deleteIconMargin: "+deleteIconMargin);
            LogUtil.d(TAG, "deleteIconLeft: "+deleteIconLeft);
            LogUtil.d(TAG, "deleteIconRight: "+deleteIconRight);
            LogUtil.d(TAG, "deleteIconBottom: "+deleteIconBottom);
            LogUtil.i(TAG, "---------------------------------------------------");
            deleteDrawable.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
            deleteDrawable.draw(c);
        } else { // view is unSwiped
            mBackground.setBounds(0, 0, 0, 0);
            deleteDrawable.draw(c);
        }

        //mBackground.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);


    }

    private void clearCanvas(Canvas c, Float left, Float top, Float right, Float bottom) {
        c.drawRect(left, top, right, bottom, mClearPaint);

    }

    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return 0.5f;
    }

}
