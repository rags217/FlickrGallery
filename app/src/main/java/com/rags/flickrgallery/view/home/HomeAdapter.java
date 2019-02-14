package com.rags.flickrgallery.view.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.rags.flickrgallery.R;
import com.rags.flickrgallery.api.home.ImageDownloader;
import com.rags.flickrgallery.model.home.Item;
import com.rags.flickrgallery.util.home.SaveImage;
import com.rags.flickrgallery.util.home.ShareImage;
import com.rags.flickrgallery.util.home.ViewImage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class HomeAdapter extends ArrayAdapter {

    //List of image and meta data
    ArrayList<Item> items;

    //temporary bitmap cache. This helps cache the image and avoids downloading the image again and again while scrolling.
    private LruCache<String, Bitmap> mMemoryCache;

    Context context;

    public HomeAdapter(Context context, int resource, ArrayList<Item> items) {
        super(context, resource, items);
        this.items = items;

        //set cache size based on the max memory to be used by checking android runtime
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Item getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.hashCode();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        //Make a copy of view, context and item
        View vi = view;
        //Holds list item info
        final ViewHolder holder;
        context = parent.getContext();
        Item item = items.get(position);


        //Create view if view is null. else re use from existing view
        if(view==null){
            vi = LayoutInflater.from(context).inflate(R.layout.fg_list_item, null);

            //Store view information in view holder to load them without recreating views each time
            holder = new ViewHolder();
            holder.image = (ImageView) vi.findViewById(R.id.image);
            holder.title = (TextView) vi.findViewById(R.id.title);
            holder.author = (TextView) vi.findViewById(R.id.author);
            holder.published = (TextView) vi.findViewById(R.id.published);

            //Image size hard coded due to variable size of images. In future to change the gallery structure to accommodate the same.
            holder.image.requestLayout();
            holder.image.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 240, parent.getContext().getResources().getDisplayMetrics());
            holder.image.getLayoutParams().width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 240, parent.getContext().getResources().getDisplayMetrics());
            vi.setTag(holder);
        }
        else{
            holder = (ViewHolder) vi.getTag();
        }

        holder.image.setImageResource(R.drawable.dummy);
        holder.urlString = item.getMedia().getM();
        holder.link = item.getLink();


        holder.title.setText(item.getTitle());
        if(item.getAuthor().isEmpty())
            holder.author.setText(context.getString(R.string.unknown_author));
        else
            holder.author.setText(context.getString(R.string.author)+" "+item.getAuthor());

        String dtStart = item.getPublished();
        SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat to = new SimpleDateFormat(" dd MMM yyyy HH:mm");
        Date date = null;
        try {
            date = from.parse(dtStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(date != null)
            holder.published.setText(context.getString(R.string.published_on)+to.format(date));
        else
            holder.published.setText(context.getString(R.string.unknown_publish_date));


        Bitmap cachedBitmap = getBitmapFromMemCache(holder.urlString);
        //If Bitmap is available in cache, load from cache. Else download asynchronously
        if (cachedBitmap != null) {
            holder.image.setImageBitmap(cachedBitmap);
        } else {
            //Pass cache and holder object so that bitmap can be set to the holder and added to the cache for future use.
            new ImageDownloader(mMemoryCache).execute(holder);
        }

        ImageButton viewInBrowser = (ImageButton)vi.findViewById(R.id.view_in_browser);
        viewInBrowser.setOnClickListener(new View.OnClickListener() {

            //Handle when view icon is clicked
            @Override
            public void onClick(View v) {

                new ViewImage().viewInBrowser(holder.link, context);
            }
        });
        ImageButton save = (ImageButton)vi.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {

            //Handle when save icon is clicked
            @Override
            public void onClick(View v) {
                //Save image from cache if not null
                if(mMemoryCache.get(holder.urlString) != null) {
                    confirmSave(holder);
                }
            }
        });

        ImageButton shareViaEmail = (ImageButton)vi.findViewById(R.id.share_via_email);
        shareViaEmail.setOnClickListener(new View.OnClickListener() {

            //Handle when share icon is clicked
            @Override
            public void onClick(View v) {
                new ShareImage().shareViaEmail(holder.link, context);
            }
        });

        return vi;
    }

    //Confirm dialog for saving image to gallery
    //@param: holder object which contains key for bitmap in bitmap cache
    private void confirmSave(final ViewHolder holder) {
        AlertDialog.Builder dialogAlert = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.action_save))
                .setMessage(context.getString(R.string.action_save_message))
                .setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new SaveImage().save(holder, context, mMemoryCache);
                    }
                });



        dialogAlert.show();
    }

    //Gets Bitmat for the key from the cache
    //@param: key for which bitmap is stored against. image url is the key
    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }
}
