package Engine.Math;

public class Matrix4f {
	public static final int size = 4;
	private float[] elements = new float[size * size];

	public static Matrix4f identity(){
		Matrix4f result = new Matrix4f();
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				result.set(i, j, 0);
			}
		}
		result.set(0, 0, 1);
		result.set(1, 1, 1);
		result.set(2, 2, 1);
		result.set(3, 3, 1);
		return result;
	}

	public static Matrix4f translate(Vector3f value){
		Matrix4f result = Matrix4f.identity();
		result.set(3, 0, value.x);
		result.set(3, 1, value.y);
		result.set(3, 2, value.z);
		return result;
	}

	public static Matrix4f rotate(float angle, Vector3f u){
		Matrix4f rotation = Matrix4f.identity();
		float cos = (float)Math.cos(Math.toRadians(angle));
		float icos = (float)(1-cos);
		float sin = (float)Math.sin(Math.toRadians(angle));
		Vector3f u2 = new Vector3f(u.x * u.x, u.y * u.y, u.z * u.z);

		rotation.set(0, 0, cos + u2.x * icos);
		rotation.set(0, 1, u.x * u.y * icos - u.z * sin);
		rotation.set(0, 2, u.x * u.z * icos + u.y * sin);

		rotation.set(1, 0, u.y * u.x * icos + u.z * sin);
		rotation.set(1, 1, cos + u2.y * icos);
		rotation.set(1, 2, u.y * u.z * icos - u.x * sin);

		rotation.set(2, 0, u.z * u.x * icos - u.y * sin);
		rotation.set(2, 1, u.z * u.y * icos + u.x * sin);
		rotation.set(2, 2, cos + u2.z * icos);




		return rotation;
	}

	public static Matrix4f scale(Vector3f scl){
		Matrix4f result = Matrix4f.identity();
		result.set(0, 0, scl.x);
		result.set(1, 1, scl.y);
		result.set(2, 2, scl.z);
		return result;
	}

	public static Matrix4f mult(Matrix4f A, Matrix4f B){
		Matrix4f result = Matrix4f.identity();
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				result.set(i, j, A.get(i, 0) * B.get(0, j) +
								 A.get(i, 1) * B.get(1, j) +
								 A.get(i, 2) * B.get(2, j) +
						         A.get(i, 3) * B.get(3, j));
			}
		}
		return result;
	}

	public static Matrix4f project(float ratio, float near, float far, float fov){
		Matrix4f result = Matrix4f.identity();
		float tan = (float)Math.tan(Math.toRadians(fov/2));

		//this piece of shit
		float range = -(near - far);
		////////////////////
		result.set(0, 0, 1.0f /(ratio * tan));
		result.set(1, 1, 1.0f /tan);
		result.set(2, 2, -((far + near)/range));
		result.set(2, 3, -1.0f );
		result.set(3, 2, -((2 * far * near)/range));
		result.set(3, 3, 0.0f);


		return result;
	}

	public static Matrix4f view(Vector3f pos, Vector3f rot){
		Matrix4f result = Matrix4f.identity();
		Vector3f negPos = new Vector3f(-pos.x, -pos.y, -pos.z);
		Matrix4f TMat = Matrix4f.translate(negPos);
		Matrix4f RMatX = Matrix4f.rotate(rot.x, new Vector3f(1, 0, 0));
		Matrix4f RMatY = Matrix4f.rotate(rot.y, new Vector3f(0, 1, 0));
		Matrix4f RMatZ = Matrix4f.rotate(rot.z, new Vector3f(0, 0, 1));



		Matrix4f rotMat = Matrix4f.mult(RMatZ, Matrix4f.mult(RMatY, RMatX));
		result = Matrix4f.mult(TMat, rotMat);

		return result;
	}

	public static Matrix4f transform(Vector3f position, Vector3f rotation, Vector3f scale){
		Matrix4f result = Matrix4f.identity();
		Matrix4f TMat = Matrix4f.translate(position);
		Matrix4f RMatX = Matrix4f.rotate(rotation.x, new Vector3f(1, 0, 0));
		Matrix4f RMatY = Matrix4f.rotate(rotation.y, new Vector3f(0, 1, 0));
		Matrix4f RMatZ = Matrix4f.rotate(rotation.z, new Vector3f(0, 0, 1));
		Matrix4f SMat = Matrix4f.scale(scale);


		Matrix4f rotMat = Matrix4f.mult(RMatX, Matrix4f.mult(RMatY, RMatZ));
		result = Matrix4f.mult(Matrix4f.mult(SMat, rotMat), TMat);

		return result;
	}



	public float get(int x, int y) {
		return elements[y * size + x];
	}
	
	public void set(int x, int y, float value) {
		elements[y * size + x] = value;
	}

	public static void call(Matrix4f ele){
		for(int i = 0; i < size; i++){

				System.out.println("[" + ele.get(i, 0) + " , " + ele.get(i, 1) + " , " + ele.get(i, 2) + " , " + ele.get(i, 3) + "]\n");

		}
		System.out.print("end/////////////");
	}
	
	public float[] getAll() {
		return elements;
	}
}