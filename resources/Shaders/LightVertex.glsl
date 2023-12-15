#version 460 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 color;


out vec4 frag_color;

uniform mat4 obj;
uniform mat4 proj;
uniform mat4 view;


mat4 test;


void main() {


	vec4 oPosition = obj * vec4(position, 1.0f);

	gl_Position = proj * view * oPosition;

	frag_color = vec4(color, 1.0f);

}

