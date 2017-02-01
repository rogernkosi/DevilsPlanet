package nkosi.roger.manutdcom.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import nkosi.roger.manutdcom.R;
import nkosi.roger.manutdcom.model.CalendarModel;

/**
 * Created by MPHILE on 1/11/2017.
 */
public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.Holder> {

    List<CalendarModel> calendarModel;

    public CalendarAdapter(List<CalendarModel> calendarModel) {
        this.calendarModel = calendarModel;
    }

    public void populate(CalendarModel model) {
        calendarModel.add(model);
        notifyDataSetChanged();
    }

    public void refresh() {
        calendarModel.clear();
        notifyDataSetChanged();
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p/>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p/>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The parentView type of the new View.
     * @return A new ViewHolder that holds a View of the given parentView type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_calendar, parent, false);
        return new Holder(row);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p/>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p/>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle effcient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final CalendarModel model = calendarModel.get(position);
        holder.dateplayed.setText(model.datePlayed);
        holder.against.setText(model.against);
        holder.score.setText(model.score);
        holder.comp.setText(model.competition);
        holder.homeAway.setText(model.homeAway);

        holder.parentView.setSelected(calendarModel.contains(position));
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return calendarModel.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView against, homeAway, score, dateplayed,comp;
        private View parentView;

        public Holder(View itemView) {
            super(itemView);
            this.parentView = itemView;
            this.homeAway = (TextView)this.parentView.findViewById(R.id.home_away);
            this.comp = (TextView)this.parentView.findViewById(R.id.competition);
            this.score = (TextView)this.parentView.findViewById(R.id.score);
            this.against = (TextView)this.parentView.findViewById(R.id.against);
            this.dateplayed = (TextView)this.parentView.findViewById(R.id.calendar_date);
        }
    }
}
