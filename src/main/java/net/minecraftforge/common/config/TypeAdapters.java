/*
 * Minecraft Forge
 * Copyright (c) 2016.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.minecraftforge.common.config;

import java.lang.reflect.Field;
import java.util.Arrays;
//=========================================================
// Run away thar' be dragons!
//=========================================================

import com.google.common.primitives.Booleans;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Shorts;

class TypeAdapters
{
   /*
    *    boolean, boolean[], Boolean, Boolean[]
    *    float, float[], Float, Float[]
    *    double, double[], Double, Double[]
    *    byte, byte[], Byte, Byte[]
    *    char, char[], Character, Character[]
    *    short, short[], Short, Short[]
    *    int, int[], Integer, Integer[]
    *    String, String[]
    */
    static ITypeAdapter bool = new TypeAdapter() {
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), getBoolean(instance, field), comment);
        }
        public Object getValue(Property prop) {
            return prop.getBoolean();
        }
    };
    static ITypeAdapter boolA = new MapAdapter() {
        private final boolean[] failureValue = new boolean[]{};
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), getObject(instance, field, boolean[].class, failureValue), comment);
        }
        public Property getProp(Configuration cfg, String category, String name, Object value) {
            return cfg.get(category, name, (boolean[])value, null);
        }
        public Object getValue(Property prop) {
            return prop.getBooleanList();
        }
    };
    static ITypeAdapter Bool = new MapAdapter() {
        private final Boolean failureValue = false;
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), getObject(instance, field, Boolean.class, failureValue), comment);
        }
        public Property getProp(Configuration cfg, String category, String name, Object value) {
            return cfg.get(category, name, (Boolean)value, null);
        }
        public Object getValue(Property prop) {
            return prop.getBoolean();
        }
    };
    static ITypeAdapter BoolA = new MapAdapter() {
        private final Boolean[] failureValue = new Boolean[0];
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), Booleans.toArray(Arrays.asList(getObject(instance, field, Boolean[].class, failureValue))), comment);
        }
        public Property getProp(Configuration cfg, String category, String name, Object value) {
            return cfg.get(category, name, (Boolean)value, null);
        }
        public Object getValue(Property prop) {
            return Booleans.asList(prop.getBooleanList()).toArray(new Boolean[prop.getBooleanList().length]);
        }
    };
    static ITypeAdapter flt = new TypeAdapter() {
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), getFloat(instance, field), comment);
        }
        public Object getValue(Property prop) {
            return (float)prop.getDouble();
        }
    };
    static ITypeAdapter fltA = new MapAdapter() {
        private final float[] failureValue = new float[0];
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), Doubles.toArray(Floats.asList(getObject(instance, field, float[].class, failureValue))), comment);
        }
        public Property getProp(Configuration cfg, String category, String name, Object value) {
            return cfg.get(category, name, Doubles.toArray(Floats.asList((float[])value)), null);
        }
        public Object getValue(Property prop) {
            return Floats.toArray(Doubles.asList(prop.getDoubleList()));
        }
    };
    static ITypeAdapter Flt = new MapAdapter() {
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), getObject(instance, field, Float.class, 0f), comment);
        }
        public Property getProp(Configuration cfg, String category, String name, Object value) {
            return cfg.get(category, name, (Float)value, null);
        }
        public Object getValue(Property prop) {
            return (float) prop.getDouble();
        }
    };
    static ITypeAdapter FltA = new MapAdapter() {
        private final Float[] failureValue = new Float[0];
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), Doubles.toArray(Arrays.asList(getObject(instance, field, Float[].class, failureValue))), comment);
        }
        public Property getProp(Configuration cfg, String category, String name, Object value) {
            return cfg.get(category, name, Doubles.toArray(Arrays.asList((Float[])value)), null);
        }
        public Object getValue(Property prop) {
            return Floats.asList(Floats.toArray(Doubles.asList(prop.getDoubleList()))).toArray(new Float[prop.getDoubleList().length]);
        }
    };
    static ITypeAdapter dbl = new TypeAdapter() {
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), getDouble(instance, field), comment);
        }
        public Object getValue(Property prop) {
            return prop.getDouble();
        }
    };
    static ITypeAdapter dblA = new MapAdapter() {
        private final double[] failureValue = new double[0];
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), getObject(instance, field, double[].class, failureValue), comment);
        }
        public Property getProp(Configuration cfg, String category, String name, Object value) {
            return cfg.get(category, name, (double[])value, null);
        }
        public Object getValue(Property prop) {
            return prop.getDoubleList();
        }
    };
    static ITypeAdapter Dbl = new MapAdapter() {
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), getObject(instance, field, Double.class, 0d), comment);
        }
        public Property getProp(Configuration cfg, String category, String name, Object value) {
            return cfg.get(category, name, (Double)value, null);
        }
        public Object getValue(Property prop) {
            return prop.getDouble();
        }
    };
    static ITypeAdapter DblA = new MapAdapter() {
        private final Double[] failureValue = new Double[0];
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), Doubles.toArray(Arrays.asList(getObject(instance, field, Double[].class, failureValue))), comment);
        }
        public Property getProp(Configuration cfg, String category, String name, Object value) {
            return cfg.get(category, name, Doubles.toArray(Arrays.asList((Double[])value)), null);
        }
        public Object getValue(Property prop) {
            return Doubles.asList(prop.getDoubleList()).toArray(new Double[prop.getDoubleList().length]);
        }
    };
    static ITypeAdapter byt = new TypeAdapter() {
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), getByte(instance, field), comment, Byte.MIN_VALUE, Byte.MAX_VALUE);
        }
        public Object getValue(Property prop) {
            return (byte)prop.getInt();
        }
    };
    static ITypeAdapter bytA = new MapAdapter() {
        private final byte[] failureValue = new byte[0];
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), Ints.toArray(Bytes.asList(getObject(instance, field, byte[].class, failureValue))), comment);
        }
        public Property getProp(Configuration cfg, String category, String name, Object value) {
            return cfg.get(category, name, Ints.toArray(Bytes.asList((byte[])value)), null);
        }
        public Object getValue(Property prop) {
            return Bytes.toArray(Ints.asList(prop.getIntList()));
        }
    };
    static ITypeAdapter Byt = new MapAdapter() {
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), getObject(instance, field, Byte.class, (byte)0), comment, Byte.MIN_VALUE, Byte.MAX_VALUE);
        }
        public Property getProp(Configuration cfg, String category, String name, Object value) {
            return cfg.get(category, name, (Byte)value, null, Byte.MIN_VALUE, Byte.MAX_VALUE);
        }
        public Object getValue(Property prop) {
            return (byte) prop.getInt();
        }
    };
    static ITypeAdapter BytA = new MapAdapter() {
        private final Byte[] failureValue = new Byte[0];
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), Ints.toArray(Arrays.asList(getObject(instance, field, Byte[].class, failureValue))), comment);
        }
        public Property getProp(Configuration cfg, String category, String name, Object value) {
            return cfg.get(category, name, Ints.toArray(Arrays.asList((Byte[])value)), null);
        }
        public Object getValue(Property prop) {
            return Bytes.asList(Bytes.toArray(Ints.asList(prop.getIntList()))).toArray(new Byte[prop.getIntList().length]);
        }
    };
    static ITypeAdapter chr = new TypeAdapter() {
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), getChar(instance, field), comment, Character.MIN_VALUE, Character.MAX_VALUE);
        }
        public Object getValue(Property prop) {
            return (char)prop.getInt();
        }
    };
    static ITypeAdapter chrA = new MapAdapter() {
        private final char[] failureValue = new char[0];
        private int[] toPrim(char[] v) {
            if (v == null) return new int[0];
            int[] ret = new int[v.length];
            for (int x = 0; x < v.length; x++)
                ret[x] = v[x];
            return ret;
        }
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), toPrim(getObject(instance, field, char[].class, failureValue)), comment);
        }
        public Property getProp(Configuration cfg, String category, String name, Object value) {
            return cfg.get(category, name, toPrim((char[])value), null);
        }
        public Object getValue(Property prop) {
            int[] v = prop.getIntList();
            char[] ret = new char[v.length];
            for (int x = 0; x < v.length; x++)
                ret[x] = (char)v[x];
            return ret;
        }
    };
    static ITypeAdapter Chr = new MapAdapter() {
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), getObject(instance, field, Character.class, (char)0), comment, Character.MIN_VALUE, Character.MAX_VALUE);
        }
        public Property getProp(Configuration cfg, String category, String name, Object value) {
            return cfg.get(category, name, (Character)value, null, Character.MIN_VALUE, Character.MAX_VALUE);
        }
        public Object getValue(Property prop) {
            return (char) prop.getInt();
        }
    };
    static ITypeAdapter ChrA = new MapAdapter() {
        private final Character[] failureValue = new Character[0];
        private int[] toPrim(Character[] v) {
            if (v == null) return new int[0];
            int[] ret = new int[v.length];
            for (int x = 0; x < v.length; x++)
                ret[x] = v[x] == null ? 0 : v[x];
            return ret;
        }
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), toPrim(getObject(instance, field, Character[].class, failureValue)), comment);
        }
        public Property getProp(Configuration cfg, String category, String name, Object value) {
            return cfg.get(category, name, toPrim((Character[])value), null);
        }
        public Object getValue(Property prop) {
            int[] v = prop.getIntList();
            Character[] ret = new Character[v.length];
            for (int x = 0; x < v.length; x++)
                ret[x] = (char) v[x];
            return ret;
        }
    };
    static ITypeAdapter shrt = new TypeAdapter() {
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), getShort(instance, field), comment, Short.MIN_VALUE, Short.MAX_VALUE);
        }
        public Object getValue(Property prop) {
            return (short)prop.getInt();
        }
    };
    static ITypeAdapter shrtA = new MapAdapter() {
        private final short[] failureValue = new short[0];
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), Ints.toArray(Shorts.asList(getObject(instance, field, short[].class, failureValue))), comment);
        }
        public Property getProp(Configuration cfg, String category, String name, Object value) {
            return cfg.get(category, name, Ints.toArray(Shorts.asList((short[])value)), null);
        }
        public Object getValue(Property prop) {
            return Shorts.toArray(Ints.asList(prop.getIntList()));
        }
    };
    static ITypeAdapter Shrt = new MapAdapter() {
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), getObject(instance, field, Short.class, (short)0), comment, Short.MIN_VALUE, Short.MAX_VALUE);
        }
        public Property getProp(Configuration cfg, String category, String name, Object value) {
            return cfg.get(category, name, (Short)value, null, Short.MIN_VALUE, Short.MAX_VALUE);
        }
        public Object getValue(Property prop) {
            return (short) prop.getInt();
        }
    };
    static ITypeAdapter ShrtA = new MapAdapter() {
        private final Short[] failureValue = new Short[0];
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), Ints.toArray(Arrays.asList(getObject(instance, field, Short[].class, failureValue))), comment);
        }
        public Property getProp(Configuration cfg, String category, String name, Object value) {
            return cfg.get(category, name, Ints.toArray(Arrays.asList((Short[])value)), null);
        }
        public Object getValue(Property prop) {
            int[] v = prop.getIntList();
            Short[] ret = new Short[v.length];
            for (int x = 0; x < ret.length; x++)
                ret[x] = (short) v[x];
            return ret;
        }
    };
    static ITypeAdapter int_ = new TypeAdapter() {
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), getInt(instance, field), comment, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        public Object getValue(Property prop) {
            return prop.getInt();
        }
    };
    static ITypeAdapter intA = new MapAdapter() {
        private final int[] failureValue = new int[0];
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), getObject(instance, field, int[].class, failureValue), comment);
        }
        public Property getProp(Configuration cfg, String category, String name, Object value) {
            return cfg.get(category, name, (int[])value, null);
        }
        public Object getValue(Property prop) {
            return prop.getIntList();
        }
    };
    static ITypeAdapter Int = new MapAdapter() {
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), getObject(instance, field, Integer.class, 0), comment, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        public Property getProp(Configuration cfg, String category, String name, Object value) {
            return cfg.get(category, name, (Integer)value, null, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        public Object getValue(Property prop) {
            return prop.getInt();
        }
    };
    static ITypeAdapter IntA = new MapAdapter() {
        private final Integer[] failureValue = new Integer[0];
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), Ints.toArray(Arrays.asList(getObject(instance, field, Integer[].class, failureValue))), comment);
        }
        public Property getProp(Configuration cfg, String category, String name, Object value) {
            return cfg.get(category, name, Ints.toArray(Arrays.asList((Integer[])value)), null);
        }
        public Object getValue(Property prop) {
            return Ints.asList(prop.getIntList()).toArray(new Integer[prop.getIntList().length]);
        }
    };
    static ITypeAdapter.Map Str = new MapAdapter() {
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), getObject(instance, field, String.class, ""), comment);
        }
        public Property getProp(Configuration cfg, String category, String name, Object value) {
            return cfg.get(category, name, (String)value, null);
        }
        public Object getValue(Property prop) {
            return prop.getString();
        }
    };
    static ITypeAdapter StrA = new MapAdapter() {
        private final String[] failureValue = new String[0];
        public Property getProp(Configuration cfg, String category, Field field, Object instance, String comment) {
            return cfg.get(category, field.getName(), getObject(instance, field, String[].class, failureValue), comment);
        }
        public Property getProp(Configuration cfg, String category, String name, Object value) {
            return cfg.get(category, name, (String[])value, null);
        }
        public Object getValue(Property prop) {
            return prop.getStringList();
        }
    };


    private static abstract class TypeAdapter implements ITypeAdapter
    {
        public static boolean getBoolean(Object instance, Field f)
        {
            try {
                return f.getBoolean(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
        public static int getInt(Object instance, Field f)
        {
            try {
                return f.getInt(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
        public static <T> T getObject(Object instance, Field f, Class<T> type, T failureValue)
        {
            try {
                Object object = f.get(instance);
                if (type.isInstance(object)) {
                    return type.cast(object);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return failureValue;
        }
        public static byte getByte(Object instance, Field f)
        {
            try {
                return f.getByte(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
        public static char getChar(Object instance, Field f)
        {
            try {
                return f.getChar(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
        public static double getDouble(Object instance, Field f)
        {
            try {
                return f.getDouble(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
        public static float getFloat(Object instance, Field f)
        {
            try {
                return f.getFloat(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
        public static short getShort(Object instance, Field f)
        {
            try {
                return f.getShort(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
    }
    private static abstract class MapAdapter extends TypeAdapter implements ITypeAdapter.Map {}
}
