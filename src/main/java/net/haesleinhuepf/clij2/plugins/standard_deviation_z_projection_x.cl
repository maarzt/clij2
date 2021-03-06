__kernel void standard_deviation_z_projection(
    IMAGE_dst_TYPE dst,
    IMAGE_src_TYPE src
) {
  const sampler_t sampler = CLK_NORMALIZED_COORDS_FALSE | CLK_ADDRESS_CLAMP_TO_EDGE | CLK_FILTER_NEAREST;

  const int x = get_global_id(0);
  const int y = get_global_id(1);

  float sum = 0;
  int count = 0;
  for(int z = 0; z < GET_IMAGE_DEPTH(src); z++)
  {
    sum = sum + (float)(READ_src_IMAGE(src,sampler,POS_src_INSTANCE(x,y,z,0)).x);
    count++;
  }
  float mean = (sum / count);

  sum = 0;
  for(int z = 0; z < GET_IMAGE_DEPTH(src); z++)
  {
    float value = (float)(READ_src_IMAGE(src,sampler,POS_src_INSTANCE(x,y,z,0)).x) - mean;
    sum = sum + (value * value);
  }
  float stdDev = sqrt((float2){sum / (count - 1), 0}).x;

  WRITE_dst_IMAGE(dst,POS_dst_INSTANCE(x,y,0,0), CONVERT_dst_PIXEL_TYPE(stdDev));
}
