package com.video.persistence.datasets;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MySet<V> implements IMySet<V> {

	private final Map<V, V> map;

	public MySet(Map<V, V> map) {
		this.map = map;
	}
	
	public MySet() {
		this.map = new HashMap<>();
	}
	
	
	public V get(V e) {
		return this.map.get(e);
	}
	
	//Overriden methods:

	@Override
	public boolean add(V e) {
		V v = this.map.put(e, e);
		if (v == null)
			return true;
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends V> c) {
		V v;
		boolean changed = false;
		for (V value : c) {
			v = this.map.put(value, value);
			if (v == null)
				changed = true;
		}
		return changed;
	}

	@Override
	public void clear() {
		this.map.clear();
	}

	@Override
	public boolean contains(Object o) {
		return this.map.containsKey(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return this.map.keySet().containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return this.map.keySet().isEmpty();
	}

	@Override
	public Iterator<V> iterator() {
		return this.map.keySet().iterator();
	}

	@Override
	public boolean remove(Object o) {
		Object obj = this.map.remove(o);
		if (obj == null)
			return false;
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		Object o;
		boolean changed = false;
		for (Object obj : c) {
			o = this.map.remove(obj);
			if (o != null)
				changed = true;
		}
		return changed;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		Object o;
		boolean changed = false;
		for (Object obj : this.map.keySet()) {
			if (!c.contains(obj)) {
				o = this.map.remove(obj);
				if(o != null) 
					changed = true;
			}
		}
		return changed;
	}

	@Override
	public int size() {
		return this.map.keySet().size();
	}

	@Override
	public Object[] toArray() {
		return this.map.keySet().toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return this.map.keySet().toArray(a);
	}

}
