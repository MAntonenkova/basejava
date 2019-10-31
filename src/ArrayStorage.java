import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < size() ; i++) {
            storage[i] = null;
        }
    }

    void save(Resume resume) {
        storage[size()] = resume;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size() ; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < size() ; i++) {
            if ((storage[i].uuid.equals(uuid))) {
                storage[i] = null;
            }
        }
    }

    Resume[] getAll() {
        Resume[] getAllResume = new Resume[size()];
        if (size() - 1 >= 0) System.arraycopy(storage, 0, getAllResume, 0, size() - 1);
        return getAllResume;
    }

    int size() {
        int counter = 0;
        for (int i = 0; i < 10000; i++) {
            if (storage[i] != null) {
                counter++;
            }
        }
        return counter;
    }
}
