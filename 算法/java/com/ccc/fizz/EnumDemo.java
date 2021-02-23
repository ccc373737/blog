package com.ccc.fizz;

import java.util.Date;

public enum EnumDemo {
    RED, GREEN, BLACK,
    YELLOW {
      String getInfo() {
          return "sadksal;dksald;";
      }
    },

    GRAY {
        Date getInfo() {
            return new Date();
        }
    };
}

