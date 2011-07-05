set Axis_Lib=.\lib
set Java_Cmd=java -Djava.ext.dirs=%Axis_Lib%
set Output_Path=.\source
set Package= org.eredlab.g4.esb.webservice.client
%Java_Cmd% org.apache.axis.wsdl.WSDL2Java -o%Output_Path% -p%Package% HelloWorldService.wsdl