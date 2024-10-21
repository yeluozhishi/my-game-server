$origin_path = $PWD.Path.ToString()
echo $origin_path
$path = $PWD.Path.ToString().replace("com\whk\protobuf", "")
echo $path

foreach($filepath in (Get-ChildItem $PWD.Path *.proto -Recurse)){
echo $filepath building...
..\protoc.exe --proto_path=$origin_path --java_out=$path $filepath.ToString()
}
