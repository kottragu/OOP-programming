package Delete;

import Backup.RestorePoint;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ClearingRP {
    boolean count = false;
    int _count;
    boolean date = false;
    Date _date;
    boolean size = false;
    long _size;
    ArrayList<String> последовательность;

    public ClearingRP() {
        последовательность = new ArrayList<>();
    }

    private void check(String s) {
        if (последовательность.stream().filter(str -> str.equals(s)).count() > 1)
            последовательность.remove(s);
    }

    public void setCount(int CoUnT) {
        _count = CoUnT;
        count = true;
        check("count");
        последовательность.add("count");
    }

    public void setDate(Date DaTe) {
       _date = DaTe;
       date = true;
       check("date");
       последовательность.add("date");
    }

    public void setSize(long SiZe) {
        _size = SiZe;
        size = true;
        check("size");
        последовательность.add("size");
    }

    private void checkDuplicate(ArrayList<RestorePoint> rps) {
        Set<RestorePoint> set = new HashSet<>(rps);
        rps.clear();
        rps.addAll(set);
    }

    public ArrayList<RestorePoint> Clear(ArrayList<RestorePoint> rps) {
        ArrayList<RestorePoint> removeRps = new ArrayList<>();
        if (count && rps.size() > _count) {
            for (int i = 0; i < rps.size() - _count; i++) {
                removeRps.add(rps.get(i));
            }
        }
        if (date) {
            for (RestorePoint rp: rps) {
                if (rp.getDate().before(_date)) {
                    removeRps.add(rp);
                }
            }
        }
        if (size) {
            long existSize = 0;
            for (RestorePoint rp: rps) {
                existSize += rp.getSize();
            }
            int i = 0;
            while (existSize > _size) {
                existSize -= rps.get(i).getSize();
                removeRps.add(rps.get(i));
                i++;
            }
        }
        checkDuplicate(removeRps);
        return removeRps;
    }
}
