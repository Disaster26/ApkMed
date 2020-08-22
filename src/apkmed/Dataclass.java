/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apkmed;

import java.sql.Blob;

/**
 *
 * @author Lukas
 */
public class Dataclass {

    private int field1;
    private String field2;
    private String field3;
    private String field4;
    private Blob blob;
    private int field5;

    public Dataclass(int field1, String field2, Blob blob) {
        this.field1 = field1;
        this.field2 = field2;
        this.blob = blob;
    }

    public Dataclass(int field1, String field2, String field3, String field4) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
    }
    public Dataclass(int field1, String field2, String field3, String field4, int field5) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
        this.field5 = field5;
    }
    public int getField1() {
        return field1;
    }

    public String getField2() {
        return field2;
    }

    public String getField3() {
        return field3;
    }

    public String getField4() {
        return field4;
    }
    public int getField5() {
        return field5;
    }
    public Blob getBlob() {
        return blob;
    }


    public void setField1(int field1) {
        this.field1 = field1;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public void setField4(String field4) {
        this.field4 = field4;
    }
    public void setField5(int field5) {
        this.field5 = field5;
    }
    public void setBlob(Blob blob) {
        this.blob = blob;
    }



}