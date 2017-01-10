package nkosi.roger.manutdcom.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import nkosi.roger.manutdcom.R;
import nkosi.roger.manutdcom.model.BlogModel;
import nkosi.roger.manutdcom.utils.aUtils;

/**
 * Created by MPHILE on 1/9/2017.
 */
public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.Holder> {

    private List<BlogModel> blogModel;

    public BlogAdapter(List<BlogModel> blogModel) {
        this.blogModel = blogModel;
    }

    public void populate(BlogModel model) {
        blogModel.add(model);
        notifyDataSetChanged();
    }

    public void refresh() {
        blogModel.clear();
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
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_row, parent, false);
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
    public void onBindViewHolder(final Holder holder, int position) {
        final BlogModel model = blogModel.get(position);
        holder.datecreated.setText(model.time);
        holder.description.setText(model.content);
        holder.title.setText(model.title);

        holder.parentView.setSelected(blogModel.contains(position));
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aUtils.invokeShare(holder.parentView.getContext(), model.title, model.content);
            }
        });
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return blogModel.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

        public TextView title, description, datecreated;
        public View parentView;
        Typeface typeface;

        public Holder(View itemView) {
            super(itemView);
            this.parentView = itemView;
            this.datecreated = (TextView) this.parentView.findViewById(R.id.blog_date);
            this.description = (TextView) this.parentView.findViewById(R.id.blog_content);
            this.title = (TextView) this.parentView.findViewById(R.id.blog_title);

            typeface = Typeface.createFromAsset(this.parentView.getContext().getAssets(), "fonts/arcon_regular.otf");
            this.title.setTypeface(this.typeface, Typeface.BOLD);
            this.title.setTextSize(14);

            this.description.setTypeface(this.typeface);
            this.datecreated.setTypeface(this.typeface);
            this.datecreated.setTextSize(10);
        }
    }
}
