#version 460 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 color;
layout(location = 2) in vec2 textCoord;
layout(location = 3) in vec3 normal;

out vec4 frag_color;
out vec2 passText;
out vec3 norm;
out vec4 oPosition;




uniform mat4 obj;
uniform mat4 proj;
uniform mat4 view;


mat4 test;


void main() {


	oPosition = obj * vec4(position, 1.0f);







	norm = mat3(transpose(inverse(obj))) * (normal);
	gl_Position = proj * view * oPosition;

	//frag_color = vec4(mNorm, 1.0f);
	passText = textCoord;
}

