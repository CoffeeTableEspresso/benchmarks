package bench;

import com.dslplatform.json.*;
import java.io.*;
import java.util.*;

public class TestJava {

	@CompiledJson
	public static class Root {
		public List<Model> coordinates;
	}

	public static class Model {
		public double x, y, z;
	}
	
	public static void parse(String filename) throws IOException {
		long start_time = System.currentTimeMillis();
		FileInputStream fis = new FileInputStream(filename);
		DslJson<Object> json = new DslJson<Object>();
		Root result = json.deserialize(Root.class, fis, new byte[65536]);
		double x = 0, y = 0, z = 0;
		int total = result.coordinates.size();
		for(Model m : result.coordinates) {
			x += m.x;
			y += m.y;
			z += m.z;
		}
		System.out.println(x / total);
		System.out.println(y / total);
		System.out.println(z / total);
		System.out.println("time: " + (System.currentTimeMillis()-start_time)/1e3+"s");
	}

	public static void main(String[] args) throws IOException {
		// warming
		for(int i = 0; i < 10; i++) {
			parse("1.json");
		}
	}
}

