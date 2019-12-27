
__kernel void maximum_y_projection_3d_2d (
    IMAGE_dst_max_TYPE dst_max,
    IMAGE_src_TYPE src
) {
  const sampler_t sampler = CLK_NORMALIZED_COORDS_FALSE | CLK_ADDRESS_CLAMP_TO_EDGE | CLK_FILTER_NEAREST;

  const int x = get_global_id(0);
  const int z = get_global_id(1);
  float max = 0;
  for(int y = 0; y < GET_IMAGE_IN_HEIGHT(src); y++)
  {
    float value = READ_src_IMAGE(src,sampler,(int4)(x,y,z,0)).x;
    if (value > max || y == 0) {
      max = value;
    }
  }
  WRITE_dst_max_IMAGE(dst_max,(int2)(x,z), CONVERT_dst_max_PIXEL_TYPE(max));
}