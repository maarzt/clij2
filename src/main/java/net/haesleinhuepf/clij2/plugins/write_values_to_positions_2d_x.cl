__constant sampler_t sampler = CLK_NORMALIZED_COORDS_FALSE | CLK_ADDRESS_CLAMP_TO_EDGE | CLK_FILTER_NEAREST;

__kernel void write_values_to_positions_2d
(
  IMAGE_dst_TYPE dst,
  IMAGE_src_TYPE src
)
{
  const int i = get_global_id(0);
  const int2 sourcePos = (int2)(i,0);

  const int x = READ_src_IMAGE(src,sampler, (sourcePos + (int2){0, 0})).x;
  const int y = READ_src_IMAGE(src,sampler, (sourcePos + (int2){0, 1})).x;
  const float v = READ_src_IMAGE(src,sampler, (sourcePos + (int2){0, 2})).x;

  const int2 coord = (int2){x, y};
  WRITE_dst_IMAGE(dst,coord, CONVERT_dst_PIXEL_TYPE(v));
}
