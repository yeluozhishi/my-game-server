echo $PWD.Path

$path = $PWD.Path.ToString().replace("com\whk\protobuf", "")

foreach($filepath in (Get-ChildItem $PWD.Path *.proto -Recurse)){
echo $filepath building...
..\protoc.exe --java_out=$path $filepath.ToString()
}

pause