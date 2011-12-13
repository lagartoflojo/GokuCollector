package com.plegnic.goku.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Public API for the example FinchVideo caching content provider example.
 *
 * The public API for a content provider should only contain information that
 * should be referenced by content provider clients. Implementation details
 * such as constants only used by a content provider subclass should not appear
 * in the provider API.
 */
public class GokuCollection {
    public static final int ID_COLUMN = 0;
    public static final int NAME_COLUMN = 1;
    public static final int DESCRIPTION_COLUMN = 2;
    
    
    public static final int LATITUDE_COLUMN = 3;
    public static final int LONGITUDE_COLUMN = 4;

    public static final String AUTHORITY =
            "com.plegnic.goku.collectionsprovider";

    /**
     * Videos content provider public API for more advanced videos example.
     */
    public static final class Collection implements BaseColumns {
        // This class cannot be instantiated
        private Collection() {}

        // uri references all videos
        public static final Uri COLLECTIONS_URI = Uri.parse("content://" +
                AUTHORITY + "/" + Collection.COLLECTIONS_PATH);

        public static final Uri ITEMS_URI = Uri.parse("content://" +
                AUTHORITY + "/" + Collection.COLLECTIONS_URI + "/#/" + Collection.ITEMS_PATH);
        
        public static final Uri ITEM_THUMB_URI = Uri.parse("content://" +
                AUTHORITY + "/" + Collection.COLLECTIONS_URI + "/#/" + Collection.ITEMS_PATH + "/#/" + Collection.THUMB_PATH);
        
        public static final Uri THUMB_URI = Uri.parse("content://" +
        		AUTHORITY + "/" + Collection.COLLECTIONS_URI + "/#/" + Collection.THUMB_PATH);

        /**
         * The content:// style URI for this table
         */
        public static final Uri CONTENT_URI = COLLECTIONS_URI;
        
        /**
         * Used to create collection URIs
         */
        public static final String COLLECTIONS_PATH = "collections";
        
        /**
         * Used to create content provider item URIs
         */
        public static final String ITEMS_PATH = "items";
        
        public static final String THUMB_PATH = "thumb";

        /**
         * The MIME type of {@link #CONTENT_URI} providing a directory of collections.
         */
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.plegnic.collection";
        

        /**
         * The MIME type of a {@link #CONTENT_URI} sub-directory of a single
         * collection.
         */
        public static final String CONTENT_COLLECTION_TYPE =
                "vnd.android.cursor.item/vnd.plegnic.collection";

        /**
         * The MIME type of a {@link #CONTENT_URI} sub-directory of a single
         * thumbnail.
         */
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.plegnic.item";

        /**
         * The title of the video
         * <P>Type: TEXT</P>
         */
        public static final String NAME = "name";


        /**
         * Name of the column that contains the collection description.
         */
        public static final String DESCRIPTION = "description";
        
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";


        /**
         * Name of the image data column.
         */
        public static final String _DATA = "_data";

		public static final String DEFAULT_SORT_ORDER = "_id asc";
    }
}
