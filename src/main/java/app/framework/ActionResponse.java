package app.framework;

import java.util.List;

public class ActionResponse {

    private String  responseText;          // raw html to display
    private Class<?> responseClazz;     // for table rendering
    private List<?> responseDataList;      // list of entities for table rendering

    // Factory methods
    // Used when you want to return a rendered HTML page
    public static ActionResponse ofHtml(String html) {
        ActionResponse r = new ActionResponse();
        r.responseText = html;
        return r;
    }

    // Used when you want the framework to render a table
    public static ActionResponse ofList(Class<?> clazz, List<?> dataList) {
        ActionResponse r = new ActionResponse();
        r.responseClazz    = clazz;
        r.responseDataList = dataList;
        return r;
    }

    // Used when you just want a redirect
    public static ActionResponse ofRedirect(String url) {
        ActionResponse r = new ActionResponse();
        r.responseText = "redirect:" + url;
        return r;
    }

    public String getResponseText()        { return responseText;    }
    public Class<?> getResponseClazz()     { return responseClazz;   }
    public List<?> getResponseDataList()   { return responseDataList; }
}