<html>
<body>
<!-- we ha ve to c-->
    <form name="enc"  action="form.php" method="post"  >
        name=<input type="text" name="name"/><br/>
        age=<input type="number" name="number"/><br/>
        <input type="submit"   value="Submit"/>
    </form>
    <?php
try
{
if($_SERVER["REQUEST_METHOD"]=="POST")
{
    if($_POST["name"]!=""||$_POST["name"]!=null)
$formdata["name"]=$_POST["name"];
    else
$formdata["name"]="_";

       
if($_POST["number"]!=""||$_POST["number"]!=null)
$formdata["number"]=$_POST["number"];
    else
$formdata["number"]="_";

echo json_encode($formdata);
}
}
catch(Exception $e)
{
echo "Exception";
}
//echo '<pre>';

// Outputs all the result of shellcommand "dir", and returns
// the last output line into $last_line. Stores the return value
// of the shell command in $retval.exec('C:\\wamp\\www\\mmitra\\vit\\notification\\su.mp3', $retval);

// Printing additional info

/*
system('C:\/Python34\/python.exe  C:\/wamp\/www\/mmitra\/vit\/notification\/pytho\/smsmodule.py',$er);
echo $er;
*/
// Execute the python script with the JSON data
//$result=shell_exec('mkdir sun');
$t=json_encode($formdata);
echo $t;
$s=strpos($t,"\"");
$f=strrpos($t,"\"");


for($i=$s;$i<=$f+1;$i++)
{
    if($t[$i]=='"')
       {
    $firstend=substr($t,0,$i);

    $lastend=substr($t,$i+1);
           $t=$firstend.'\"'.$lastend;
        $i++;
        $f++;
       }
    
}
       echo $t;
       
    

//echo 'C:\/Python34\/python.exe  C:\/wamp\/www\/mmitra\/vit\/notification\/pytho\/smsmodule.py \''.json_encode($formdata).'\'';
system('C:\/Python34\/python.exe  C:\/wamp\/www\/mmitra\/vit\/notification\/pytho\/smsmodule.py \"'.json_encode($formdata).'\"',$res);

//echo $res;


// Decode the result
//$resultData = json_decode($result, true);

// This will contain: array('status' => 'Yes!')
//var_dump($resultData);

?>
    
</body>
 </html>