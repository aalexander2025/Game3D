#version 460 core

const int MAX_L = 50;

in vec4 frag_color;
in vec2 passText;
in vec3 norm;
in vec4 oPosition;



out vec4 outColor;

uniform sampler2D text1;
uniform sampler2D text2;

uniform vec3 lightPos[MAX_L];
uniform vec3 lightColor[MAX_L];
uniform vec3 lightData[MAX_L];
uniform int SIZE;



//uniform sampler2D paletteMapping;
//
vec4 mapColor( vec3 C ) {


	float r = C.r * 7.0f;

	float g = C.g * 7.0f;
	float b = C.b * 63.0f;

	float x = g + (8.0f * floor(b/8.0f));
	float y = r + (8.0f * floor(mod(b, 8.0f)));

	float cx = x/64.0f;
	float cy = y/64.0f;


	float off = (1.0f/64.0f);


	return texture( text2, vec2(cx, cy));

}

float flooredDistance(vec3 a, vec3 b){
	float sub = 1.0f;
	float sqrX = floor((a.x - b.x)*(a.x - b.x));
	float sqrY = floor((a.y - b.y)*(a.y - b.y));
	float sqrZ = floor((a.z - b.z)*(a.z - b.z));
	return sqrt( (sqrX*sub) + (sqrY*sub) + (sqrZ*sub));
}


void main() {

	vec3 diffuse = vec3(0.0f);
	vec3 Norm = normalize(norm);

	for(int i = 0; i < SIZE; i++){

		vec3 lDir = normalize(lightPos[i] - vec3(oPosition));
		float diff = max(dot(Norm, lDir), 0.0f);
		float dist = length(vec3(oPosition) - lightPos[i]);
		diffuse += vec3(min(lightData[i].x/(dist*dist), 1.0f)) * (diff * lightColor[i]);
	}







//	vec3 lDir2 = normalize(light2 - vec3(oPosition));
//	float diff2 = max(dot(Norm, lDir2), 0.0f);
//	float dist2 = length(vec3(oPosition) - light2);
//	vec3 diffuse2 = vec3(min(20.0f/(dist2*dist2), 1.0f)) * (diff2 * vec3(1.0f, 0.0f, 0.0f));
//
//	vec3 lDir3 = normalize(light3 - vec3(oPosition));
//	float diff3 = max(dot(Norm, lDir3), 0.0f);
//	float dist3 = length(vec3(oPosition) - light3);
//	vec3 diffuse3 = vec3(min(20.0f/(dist3*dist3), 1.0f)) * (diff3 * vec3(0.0f, 1.0f, 0.0f));
//
//	vec3 lDir4 = normalize(light4 - vec3(oPosition));
//	float diff4 = max(dot(Norm, lDir4), 0.0f);
//	float dist4 = length(vec3(oPosition) - light4);
//	vec3 diffuse4 = vec3(min(20.0f/(dist4*dist4), 1.0f)) * (diff4 * vec3(0.0f, 0.0f, 1.0f));





	vec4 normColor = texture(text1, passText);

	outColor =    mapColor(vec3(normColor  )) * (vec4((min(diffuse, vec3(1.0f))) + vec3(0.7f), 1.0f)) ;
	//  * vec4((diffuse) + vec3(0.3f), 1.0f);
	//outColor = texture(text1, passText) * vec4((diffuse) + vec3(0.3f), 1.0f)  ;





	//outColor = vec4(1, 0, 0, 1) * vec4((diffuse + vec3(0.5f)), 1.0f);
}