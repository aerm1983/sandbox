package localhost.sandbox.jse8.Object;

public class Pojo {

	public static class Person {

		private String name;

		private int age;

		private Boolean didService;

		Person () {
			super();
		}

		Person (String name, Integer age, Boolean didService) {
			super();
			this.name = name;
			this.age = age;
			this.didService = didService;
		}

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public Boolean getDidService() {
			return didService;
		}
		public void setDidService(Boolean didService) {
			this.didService = didService;
		}

		@Override
		public String toString() {
			String out = "{ "
					+ "" + "name: " + name
					+ ", " + "age: " + age
					+ ", " + "didService: " + didService
					+ " }"
					;
			return out;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
			result = prime * result + (Integer.valueOf(this.age).hashCode());
			result = prime * result + ((this.didService) ? Boolean.TRUE.hashCode() : Boolean.FALSE.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}

			if (obj.getClass() != this.getClass()) {
				return false;
			}

			final Person other = (Person) obj;

			if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
				return false;
			}

			if (this.age != other.age) {
				return false;
			}

			if ((this.didService == null) ? (other.didService != null) : !this.didService.equals(other.didService)) {
				return false;
			}

			return true;
		}

	}

}
