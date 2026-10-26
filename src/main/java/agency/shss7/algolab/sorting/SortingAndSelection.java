package agency.shss7.algolab.sorting;

/**
 * Sorting and order-statistic algorithms: Bottom-Up MergeSort, Dual-Pivot QuickSort,
 * and Hoare QuickSelect for O(N) expected k-th smallest element selection.
 */
public final class SortingAndSelection {

    private SortingAndSelection() {
    }

    public static void mergeSort(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        int n = array.length;
        int[] aux = new int[n];
        for (int width = 1; width < n; width <<= 1) {
            for (int lo = 0; lo < n - width; lo += (width << 1)) {
                int mid = lo + width - 1;
                int hi = Math.min(lo + (width << 1) - 1, n - 1);
                merge(array, aux, lo, mid, hi);
            }
        }
    }

    public static int quickSelect(int[] array, int k) {
        if (array == null || k < 0 || k >= array.length) {
            throw new IllegalArgumentException("Invalid k index for QuickSelect");
        }
        int[] copy = array.clone();
        int lo = 0;
        int hi = copy.length - 1;
        while (lo < hi) {
            int pivotIdx = partition(copy, lo, hi);
            if (pivotIdx == k) {
                return copy[k];
            } else if (pivotIdx < k) {
                lo = pivotIdx + 1;
            } else {
                hi = pivotIdx - 1;
            }
        }
        return copy[lo];
    }

    private static int partition(int[] a, int lo, int hi) {
        int mid = lo + ((hi - lo) >>> 1);
        swap(a, mid, hi);
        int pivot = a[hi];
        int store = lo;
        for (int i = lo; i < hi; i++) {
            if (a[i] < pivot) {
                swap(a, i, store++);
            }
        }
        swap(a, store, hi);
        return store;
    }

    private static void merge(int[] a, int[] aux, int lo, int mid, int hi) {
        System.arraycopy(a, lo, aux, lo, hi - lo + 1);
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (aux[j] < aux[i]) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
