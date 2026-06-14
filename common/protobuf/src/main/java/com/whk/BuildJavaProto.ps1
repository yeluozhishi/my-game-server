$origin_path = $PWD.Path.ToString() + "\protobuf"
echo $origin_path
$path = $PWD.Path.ToString().replace("com\whk", "")
echo $path

foreach ($filepath in (Get-ChildItem $origin_path *.proto -Recurse))
{
    echo $filepath building...
    .\protoc.exe --proto_path=$origin_path --java_out=$path $filepath.ToString()
}
