#include "Conversion.h"

void Conversion::YCbCrToRGB(uint8_t* From, uint8_t* To, int length)
{
	if (length < 1) return;
	int Red, Green, Blue;
	int Y, Cb, Cr;
	int i,offset;
	for(i = 0; i < length; i++)
	{
		offset = (i << 1) + i;
		Y = From[offset]; Cb = From[offset+1] - 128; Cr = From[offset+2] - 128;
		Red = Y + ((RGBRCrI * Cr + HalfShiftValue) >> Shift);
		Green = Y + ((RGBGCbI * Cb + RGBGCrI * Cr + HalfShiftValue) >> Shift);
		Blue = Y + ((RGBBCbI * Cb + HalfShiftValue) >> Shift);
		if (Red > 255) Red = 255; else if (Red < 0) Red = 0;
		if (Green > 255) Green = 255; else if (Green < 0) Green = 0;
		if (Blue > 255) Blue = 255; else if (Blue < 0) Blue = 0;
		offset = i << 2;
		To[offset] = (uint8_t)Blue;
		To[offset+1] = (uint8_t)Green;
		To[offset+2] = (uint8_t)Red;
		To[offset+3] = 0xff;
	}
}

void Conversion::RGBToYCbCr(uint8_t* From, uint8_t* To, int length)
{
	if (length < 1) return;
	int Red, Green, Blue;
	int i,offset;
	for(i = 0; i < length; i++)
	{
		offset = i << 2;
		Blue = From[offset]; Green = From[offset+1]; Red = From[offset+2];
		offset = (i << 1) + i;
		To[offset] = (uint8_t)((YCbCrYRI * Red + YCbCrYGI * Green + YCbCrYBI * Blue + HalfShiftValue) >> Shift);
		To[offset+1] = (uint8_t)(128 + ((YCbCrCbRI * Red + YCbCrCbGI * Green + YCbCrCbBI * Blue + HalfShiftValue) >> Shift));
		To[offset+2] = (uint8_t)(128 + ((YCbCrCrRI * Red + YCbCrCrGI * Green + YCbCrCrBI * Blue + HalfShiftValue) >> Shift));
	}
}
