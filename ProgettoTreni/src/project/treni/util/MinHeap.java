package project.treni.util;

import java.util.ArrayList;
import java.util.List;

import project.treni.Stazione;

public class MinHeap {
    List<Stazione> h = new ArrayList<Stazione>();

    public MinHeap() {
    }

    public MinHeap(ArrayList<Stazione> arrayList) {
        for (Stazione key : arrayList) {
            h.add(key);

        }
        for (int k = h.size() / 2 - 1; k >= 0; k--) {
            percolateDown(k, h.get(k));
        }
    }

	public void add(Stazione node) {
		h.add(null);
		int k = h.size() - 1;
		while (k > 0) {
			int parent = (k - 1) / 2;
			Stazione p = h.get(parent);
			if (node.getPeso().compareTo(p.getPeso()) >= 0) {
				break;
			}
			h.set(k, p);
			k = parent;
		}
		h.set(k, node);
	}

	public Stazione remove() {
		Stazione removedNode = h.get(0);
		Stazione lastNode = h.remove(h.size() - 1);
		percolateDown(0, lastNode);
		return removedNode;
	}

    public Stazione min() {
        return h.get(0);
    }

    public boolean isEmpty() {
        return h.isEmpty();
    }

    void percolateDown(int k, Stazione node) {
        if (h.isEmpty()) {
            return;
        }
        while (k < h.size() / 2) {
            Integer child = 2 * k + 1;
            if (child < h.size() - 1
                    && h.get(child).getPeso()
                            .compareTo(h.get(child + 1).getPeso()) > 0) {
                child++;
            }
            if (node.getPeso().compareTo(h.get(child).getPeso()) <= 0) {
                break;
            }
            h.set(k, h.get(child));
            k = child;
        }
        h.set(k, node);
    }
}