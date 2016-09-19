package be.elmoumene.expense.note;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

	/**
	 * Enumeration L_EventType.
	 *
	 */
	public enum L_EventType implements java.io.Serializable {
	    DEFAULT {
	        public String toCode() {
	            return "default";
	        }

	        public Integer getPriority() {
	            return 1;
	        };
	    },

	    CSO {
	        public String toCode() {
	            return "cso";
	        }

	        public Integer getPriority() {
	            return 2;
	        };
	    },

	    RH {
	        public String toCode() {
	            return "rh";
	        }

	        public Integer getPriority() {
	            return 3;
	        };
	    },

	    BIRTHDAY {
	        public String toCode() {
	            return "birthday";
	        }

	        public Integer getPriority() {
	            return 4;
	        };
	    },

	    HOLIDAY {
	        public String toCode() {
	            return "holiday";
	        }

	        public Integer getPriority() {
	            return 5;
	        };
	    },
	    WELLNESS {
	        public String toCode() {
	            return "wellness";
	        }

	        public Integer getPriority() {
	            return 6;
	        };
	    };

	    private L_EventType() {
	    }

	    public String toCode() {
	        return "";
	    }

	    public Integer getPriority() {
	        return 0;
	    };

	    static public L_EventType fromCode(String code) {
	        if (code == null || code.equals(""))
	            return null;
	        if (code.equals("default"))
	            return L_EventType.DEFAULT;
	        if (code.equals("cso"))
	            return L_EventType.CSO;
	        if (code.equals("rh"))
	            return L_EventType.RH;
	        if (code.equals("birthday"))
	            return L_EventType.BIRTHDAY;
	        if (code.equals("holiday"))
	            return L_EventType.HOLIDAY;
	        if (code.equals("wellness"))
	            return L_EventType.WELLNESS;
	        throw new RuntimeException("Enumeration L_EventType does not contains code: " + code);
	    }

	    /**
	     * Return the L_EventType from a string value
	     *
	     * @return L_EventType enum object
	     */
	    public static L_EventType fromString(java.lang.String value) {
	        return valueOf(value);
	    }

	    /**
	     * Return a Collection of all literal values for this enumeration
	     *
	     * @return java.util.Collection literal values
	     */
	    public static java.util.Collection<L_EventType> literals() {
	        final java.util.Collection<L_EventType> literals = new java.util.ArrayList<L_EventType>(values().length);
	        for (int i = 0; i < values().length; i++) {
	            literals.add(values()[i]);
	        }
	        return literals;
	    }

	    /**
	     * Return a Collection of all literal values for this enumeration
	     *
	     * @return java.util.Collection literal values
	     */
	    public static java.util.Collection<String> literalsCode() {
	        final java.util.Collection<String> literalsCode = new java.util.ArrayList<String>(values().length);
	        for (int i = 0; i < values().length; i++) {
	            literalsCode.add(values()[i].toCode());
	        }
	        return literalsCode;
	    }

	    /**
	     * Convert table to List L_EventType.
	     *
	     * @param types
	     *            the types table
	     * @return list of L_EventType
	     *
	     * @author sr
	     */
	    public static List<L_EventType> toList(String[] types) {
	        List<L_EventType> typeList = new ArrayList<L_EventType>();
	        for (String lng : types) {
	            typeList.add(L_EventType.fromCode(lng));
	        }
	        return typeList;
	    }

	    /**
	     * return the Locale from type
	     *
	     * @param type
	     * @return
	     *
	     * @author sr
	     */
	    public static Locale toLocale(L_EventType type) {
	        return new Locale(type.toCode().split("_")[0], type.toCode().split("_")[1]);
	    }

}
