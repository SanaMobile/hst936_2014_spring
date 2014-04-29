

<?php 


function modify_formdata_to_customizejson()
{
$formquestion=array("q1"=>"Fits","q2"=>"Constant_vomiting","q3"=>"Child_isn't_eating/drinking_anything","q4"=>"Child_indrawing");

        
try
{
    static $count=19;
   
if($_SERVER["REQUEST_METHOD"]=="POST")
{
   
    $tmp="{";
    foreach($_POST as $key=>$value)
    { 
        if($value!=""||$value!=null)
        {
            $formd["question"]=$formquestion[$key];
            $formd["answer"]=$value;
             
          
            $tmp.="\"".$count."\":".json_encode($formd).",";
          
            // var_dump($formdata);
            
        }
         $count=$count+1;
        
       
    }
    $tmp=substr($tmp,0,-1);
    $tmp.="}";
    
    //$f=strrpos($tmp,",");
    //$tmp[$f]="}";
    
}


}

catch(Exception $e)
{
echo "Exception";
}
$t=$tmp;

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
return $t;
}
?>