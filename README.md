# ReduceIt

  This application compresses the file by implementing 4-way cache optimized heap and Huffman coding algorithm in Java. It can encode and decode the enormous amount of data to be streamed over the network.
  
The sample input and output files are provided in the folder Sample1 and Sample2. Sample1 contains small input file. Sample2 contains large input file.

  The encoder requires one input file, which is sample_input_small.txt. Encoder will produce two files: 
  1. encode.bin which is the compressed file. 
  2. code_table.txt which is used by the decoder to decode the encode.bin file.
  
  The decoder requires two input files : encoded.bin and code_table.txt (Both files are produced by the encoder). Decoder will produce one file :
  1. decoded.txt
  
  If everything goes well, the decoded.txt will be exactly same as sample_input_small.txt. Which is the required result.
  
  ### Workflow :
  
  Encoder :
  
  ![encoder](https://cloud.githubusercontent.com/assets/22841389/25728543/ce7a7a66-30fd-11e7-86c1-53943a989817.PNG)
  
  Decoder :
  
  ![decoder](https://cloud.githubusercontent.com/assets/22841389/25728560/e490c436-30fd-11e7-928d-eef3cd74c6ce.PNG)

  
