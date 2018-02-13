package keybinds.dto;

public class KeyBind {

	String kind = null;
	String key = null;
	String path = null;
	String name = null;

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((kind == null) ? 0 : kind.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		KeyBind other = (KeyBind) obj;

		if (key == null) {

			if (other.key != null)
				return false;

		} else if (!key.equals(other.key))
			return false;

		if (kind == null) {
			if (other.kind != null)
				return false;
		} else if (!kind.equals(other.kind))
			return false;

		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

	public boolean equalsIgnoreCase(KeyBind keyBind) {

		String kind = keyBind.getKind();
		String key = keyBind.getKey();

		if (this.kind == null || this.key == null || kind == null || key == null)
			return false;

		if (this.kind.equalsIgnoreCase(kind) && this.key.equalsIgnoreCase(key))
			return true;

		return false;
	}

	@Override
	public String toString() {

		int length = name.length();
		for (; length <= 8; length++) {
			name += " ";
		}

		return name + " " + kind + "->" + key + " path=" + path + "";
	}

}
