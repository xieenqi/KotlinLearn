package com.mykotlin.test;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import static android.R.id.message;

/**
 * Created by xeq on 17/2/22.
 */
@Root(name = "xml", strict = false)
public class XMLService {
    @Element(name = "status")
    public String status;

    @Element(name = "message")
    public String message;
}
