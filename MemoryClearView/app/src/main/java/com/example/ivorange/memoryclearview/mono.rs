#pragma version(1)
#pragma rs java_package_name(com.example.ivorange.memoryclearview)

rs_allocation gIn;
rs_allocation gOut;

rs_script gScript;

const static float3 gMonoMult={0.299f,0.587f,0.114f};

void root(const uchar4 *v_in, uchar4 *v_out, const void *usrData, uint32_t x, uint32_t y){

//将一个uchar4 的颜色解压为float4
	float4 f4=rsUnpackColor8888(*v_in);

	//dot:[0]*[0]+[1]*[1]+[2]*[2]
	float3 mono=dot(f4.rgb,gMonoMult);
	//打包uchar4，alpha 默认为1.0
	*v_out=rsPackColorTo8888(mono);

}

void filter(){
//调用RenderScript 进行处理(操作输入的图片然后把处理的结果放到输出结果中)，最后把处理完的数据存回bitmap ，在ImageView显示出来
	 rsForEach(gScript, gIn, gOut);
}