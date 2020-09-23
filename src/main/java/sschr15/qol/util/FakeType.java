package sschr15.qol.util;

import java.lang.reflect.Type;

public class FakeType implements Type {
    @Override
    public String getTypeName() {
        return "This is a fake type!";
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
