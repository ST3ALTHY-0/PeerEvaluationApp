package edu.pui.peerEvaluation.Peerevualuationapplication.brightSpaceApi.brightSpacePagedResults;

import java.util.List;


//class to hold paged results from brightSpace api, i.e classList and enrollments will return a paged result set of students and classes respectively
//we use the generic <T> to allow classes to use this class to hold whatever objs we want
public class PagedResultSet<T> {
    private PagingInfo PagingInfo;
    private List<T> Items;

    // Getters and setters

    public PagingInfo getPagingInfo() {
        return PagingInfo;
    }

    public void setPagingInfo(PagingInfo pagingInfo) {
        PagingInfo = pagingInfo;
    }

    public List<T> getItems() {
        return Items;
    }

    public void setItems(List<T> items) {
        Items = items;
    }

    public static class PagingInfo {
        private String Bookmark;
        private boolean HasMoreItems;

        // Getters and setters

        public String getBookmark() {
            return Bookmark;
        }

        public void setBookmark(String bookmark) {
            Bookmark = bookmark;
        }

        public boolean isHasMoreItems() {
            return HasMoreItems;
        }

        public void setHasMoreItems(boolean hasMoreItems) {
            HasMoreItems = hasMoreItems;
        }
    }

        //brightSpace pagedResultSet
    // {
    //     "PagingInfo": {
    //        "Bookmark": <string>,
    //        "HasMoreItems": <boolean>
    //     },
    //     "Items": [
    //         { <composite:first_item_in_this_set> },
    //         { <composite:second_item_in_this_set>}, ...
    //         { <composite:nth_item_in_this_set> }
    //     ]
     
}