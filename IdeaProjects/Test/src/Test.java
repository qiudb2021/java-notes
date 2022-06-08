import java.util.Calendar;
import java.util.TimeZone;

public class Test {
//    public static void main(String[] args) {
//        System.out.println("This is a Test Project.");
//
//        util.ArrayBox arrayBox = new util.ArrayBox(15);
//        System.out.println("length " + arrayBox.getElementData().length);
//        System.out.println("size " + arrayBox.getSize());
//        for (int i = 0; i < 20; i++) {
//            arrayBox.add(i * 10);
//        }
//
////        for (int i = 0; i < arrayBox.size; i++) {
////            System.out.println(arrayBox.elementData[i]);
////        }
//
////        System.out.println(arrayBox.elementData.length);
////        System.out.println(arrayBox.size);
//
//        System.out.println(arrayBox.get(15));
//        System.out.println(arrayBox.remove(15));
//        System.out.println(arrayBox.get(15));
//        System.out.println(arrayBox.getSize());
//
//    }
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();

        System.out.println(calendar);
        System.out.println(calendar.after(calendar1)); // false;
        System.out.println(calendar.equals(calendar1)); // false
        System.out.println(calendar.before(calendar1)); // true
        System.out.println(calendar.get(Calendar.YEAR)); // 2022
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH)); // 30

//        calendar.set(Calendar.YEAR, 2021);
//        System.out.println(calendar.get(Calendar.YEAR)); // 2021

        TimeZone tz = calendar.getTimeZone();
        System.out.println(tz.getID()); // Asia/Shanghai
        System.out.println(tz.getDisplayName()); // 中国标准时间
    }
}
