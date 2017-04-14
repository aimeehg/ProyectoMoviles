package com.redes.boui.tabbed;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.HashMap;

/**
 * Created by Boui on 13/04/2017.
 */
public class MyCustomXAxisValueFormatter implements IAxisValueFormatter
{
    private String[] mValues;

    public MyCustomXAxisValueFormatter(String[] values) {
        this.mValues = values;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        return mValues[(int) value];
    }

    /** this is only needed if numbers are returned, else return 0 */

    public int getDecimalDigits() { return 0; }
}