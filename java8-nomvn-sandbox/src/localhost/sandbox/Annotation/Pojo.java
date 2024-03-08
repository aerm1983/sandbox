package localhost.sandbox.Annotation;

import localhost.sandbox.Annotation.Annotation.DbFieldEncryptTest;
import localhost.sandbox.Annotation.Annotation.SizeTest;

public class Pojo {
	
	public static class Person {
		
		@SizeTest(max=10)
		@DbFieldEncryptTest
		private String name;
		
		private Integer age;
		
		private Boolean didService;
		
		Person () {
		}
		
		Person (String name, Integer age, Boolean didService) {
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
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}
		public Boolean getDidService() {
			return didService;
		}
		public void setDidService(Boolean didService) {
			this.didService = didService;
		}
		
		public String toString() {
			String out = "{"
					+ "name:" + name + ", "
					+ "age:" + age + ", "
					+ "didService:" + didService + ""
					+ "}"
					;
			return out;
		}
	}
}
