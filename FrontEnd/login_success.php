<?php session_start(); ?>
<!DOCTYPE HTML>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width">
<title>Code Testing</title>
<link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro' rel='stylesheet' type='text/css'>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
<!-- Start of Page -->
<div id="page">

<!-- Start of Header -->
<div id="header">
    
    <!-- Start of Banner -->
    <div id="banner">
        
        <!-- Start of Logo -->
        <div id="logo">
        <a href="./index.php"><h1>Code Testing</h1></a>
        </div><!-- End of Logo -->
    
    </div> <!-- End of Banner -->
   
     <!-- Start of Welcome Div -->
    <div id="welcome">
        
	<?php 
		echo "Welcome " . $_SESSION["user"]; 
		if ($_SESSION["usertype"] = "UCID") {
			echo ", you are now logged in with your UCID";
		}
		else if ($_SESSION["usertype"] = "USERNAME") {
			echo ", you are now logged in with your Username";
		}
	?>
    
    </div> <!-- End of Welcome Div -->

</div> <!-- End of Header -->

<!-- Start of Main Content -->
<div id="main">
<div id="content">
<p>
Lorem ipsum dolor sit amet, consectetur adipiscing elit. In mattis at augue vel varius. Vestibulum ante nisl, vulputate a felis a, maximus consequat sapien. Sed vehicula leo a ornare elementum. Aenean efficitur dui id nulla convallis, eget accumsan erat accumsan. Pellentesque quis nulla fringilla, faucibus metus sed, vestibulum enim. Sed ac lectus orci. Sed posuere et orci et congue.
</p>
<p>
Mauris posuere tincidunt odio vitae placerat. Praesent et lobortis eros. In pellentesque sit amet nunc eu aliquet. Nam vitae arcu nec quam ultricies aliquet ac commodo sapien. Fusce ut aliquam sem. Duis scelerisque libero blandit diam commodo varius. Integer sem felis, vehicula eu tristique vel, varius ut ex. Morbi dictum nulla quis turpis consequat molestie. Proin vel nibh sit amet urna hendrerit rutrum non et leo. Vivamus ultricies mauris vel risus bibendum consectetur. Aliquam quis efficitur arcu, at congue odio.
</p>
<p>
Cras eu felis odio. In quis felis eu nisl scelerisque placerat sed id ante. Cras lorem orci, sagittis laoreet accumsan sit amet, consectetur iaculis lacus. Sed placerat est id enim sollicitudin, at malesuada est hendrerit. Curabitur ultrices sed nisl sed mattis. In pulvinar neque ac nibh consequat lacinia. Phasellus interdum ante sit amet nulla vestibulum, ut cursus nulla rhoncus.
</p>
<p>
Duis quis sem sit amet purus laoreet dictum. Pellentesque posuere ipsum vulputate, malesuada sem eget, posuere neque. Donec et tincidunt est, nec hendrerit mi. Donec vehicula egestas consequat. Sed scelerisque dui vitae magna rhoncus, sed lacinia sapien bibendum. Vivamus nisl lorem, malesuada non tincidunt nec, facilisis id sem. Integer mattis augue feugiat orci fermentum, et volutpat tortor tincidunt. Vestibulum et pretium felis, eu iaculis neque. Integer fringilla ultricies purus, a efficitur lectus rhoncus ut. Aenean lectus magna, feugiat non ipsum quis, molestie accumsan augue. Quisque gravida fringilla sagittis. Nullam a vehicula turpis. Nullam eros urna, faucibus sed ornare sit amet, egestas viverra ante. Nam ac aliquet enim. Pellentesque sit amet lacus dui. Etiam at nunc ultrices, facilisis leo dapibus, vehicula orci.
</p>
<p>
Fusce aliquam sed velit vel aliquam. Nunc placerat lectus vitae libero elementum scelerisque. Donec molestie magna elementum nibh porta fringilla. In in finibus sapien, non finibus nisi. Nulla nisl mi, condimentum nec ligula ac, aliquam sodales tellus. Cras at ex ex. Pellentesque facilisis neque eget euismod luctus. Pellentesque nulla metus, varius non dui vitae, fermentum ultricies nisi.
</p>
<p>
Sed iaculis sapien nunc, sit amet porttitor lectus rutrum faucibus. Vestibulum vel malesuada urna. Vestibulum vehicula laoreet mi nec venenatis. Aenean malesuada eget metus vel hendrerit. Curabitur rhoncus vitae dui quis viverra. Ut commodo, quam quis tincidunt condimentum, leo dui dictum tortor, ut iaculis tortor lacus finibus est. Integer nec hendrerit sem. Vestibulum a placerat odio. In ac efficitur magna. Aliquam erat volutpat. Vestibulum metus massa, molestie eget magna a, sollicitudin posuere neque. Quisque nec dictum nisi. In massa diam, facilisis non urna eu, dictum posuere felis. Curabitur tempor mollis purus, sit amet rhoncus lacus pretium non. Proin vel pellentesque eros, ut ullamcorper dui. Fusce a tempor massa.
</p>
<p>
Quisque id hendrerit enim. Donec accumsan dui eget felis porttitor, id aliquam enim dignissim. In sodales, mauris placerat accumsan feugiat, purus quam lobortis mi, eget porta ante tortor ut metus. Suspendisse at dui eget elit varius consequat porta vel eros. Nam justo arcu, condimentum et mattis sit amet, mollis et mauris. Suspendisse mattis lacus nec lorem bibendum mattis. Integer semper est leo, vel faucibus tortor mollis sed. Phasellus maximus tortor in massa fringilla dignissim. Nulla elementum ligula ut leo malesuada maximus. Nam elementum aliquet neque vitae efficitur. Donec id nunc id ipsum aliquam faucibus eu vel lorem. Phasellus euismod nulla non molestie gravida. Sed sem nisl, condimentum sed tortor vitae, pulvinar suscipit mi. Sed lacus ipsum, tincidunt vel libero a, gravida placerat sem.
</p>
<p>
Ut eget rutrum velit. Aliquam sed nunc ut orci sollicitudin eleifend. Aliquam commodo dolor ut nibh ullamcorper, ut egestas turpis venenatis. Vestibulum mollis leo ac lacinia lobortis. Donec in aliquam ligula, eu sodales justo. Quisque scelerisque suscipit mi sed pretium. Curabitur magna urna, eleifend eu quam vitae, scelerisque ultricies nisi. Proin fermentum risus vel tortor porta aliquam. Pellentesque elit orci, ullamcorper ultrices imperdiet nec, porta nec sapien. Vivamus nulla ex, blandit vitae varius in, pretium sed lorem. Fusce fringilla porttitor suscipit. Proin tristique dapibus dui, id consectetur neque finibus ultricies. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nam ut ullamcorper lorem.
</p>
<p>
Fusce suscipit sed metus luctus venenatis. Integer eu nunc ex. Curabitur enim ante, varius eget sem et, venenatis sodales diam. Maecenas at mauris erat. Etiam porta faucibus cursus. Fusce sit amet enim ipsum. Morbi vitae massa ac felis rutrum dictum. Sed laoreet neque non ante fringilla posuere. Vivamus ultricies aliquet dui non posuere. Morbi tempus tristique vehicula. Morbi lacinia suscipit ante sit amet interdum. Donec non vestibulum sapien. Proin feugiat efficitur lorem, sed dictum dui pellentesque sed.
</p>
<p>
Mauris urna quam, eleifend vel molestie et, ultrices sed mauris. Vestibulum ut ex gravida, aliquet neque vel, pulvinar libero. Integer dolor enim, faucibus ut pellentesque posuere, tincidunt eu turpis. Duis accumsan lacus ante, nec ultricies mauris pellentesque sit amet. Maecenas sed scelerisque sem, non porta libero. Vivamus elementum sem non nulla auctor hendrerit. Donec orci felis, fermentum vitae mauris id, finibus porta quam. Aliquam erat volutpat. Etiam condimentum, leo sed hendrerit tristique, diam lorem placerat elit, sit amet tempor quam ipsum a dui. Nam facilisis arcu quis laoreet semper.
</p>
<p>
Quisque et quam diam. Morbi libero neque, egestas quis metus ac, aliquam faucibus quam. Morbi varius diam ac libero vestibulum laoreet. Nunc dapibus dolor nisi, et laoreet felis sagittis quis. Proin porttitor lacus massa. Integer vehicula enim ullamcorper dapibus venenatis. Sed vel lorem mi. Ut vitae libero mauris. Cras dui mauris, porta ac tincidunt a, tempus a odio.
</p>
<p>
Ut tempus porta scelerisque. Quisque at interdum eros, ut sollicitudin ligula. Nunc et laoreet justo, et eleifend justo. Nulla eget dapibus magna. Aenean malesuada nisi id leo viverra iaculis. Morbi imperdiet dui vitae laoreet lobortis. Phasellus scelerisque mauris non bibendum consequat. Fusce id ultricies enim.
</p>
<p>
Nam sed erat ut risus bibendum gravida vel vitae orci. Curabitur scelerisque et ligula vitae efficitur. Nullam a tortor dapibus, aliquet neque et, tincidunt lorem. Fusce ornare scelerisque leo viverra egestas. Nunc vel sapien id libero vulputate feugiat. Phasellus consectetur commodo lorem. Suspendisse id ligula mi. Aenean pulvinar sit amet nunc id posuere. Duis placerat et libero vel vulputate. Quisque porttitor placerat erat. Sed eget arcu convallis, rhoncus tellus at, feugiat leo. Vestibulum consectetur sed nulla vitae posuere. Ut dictum quam in leo dignissim rutrum.
</p>
<p>
Nullam lobortis neque at ultrices feugiat. Nullam euismod urna a nibh mollis, consectetur scelerisque nibh gravida. Nunc pellentesque nisi malesuada, condimentum mi id, molestie arcu. Nam ut purus quis eros elementum posuere. Sed erat nibh, euismod eu imperdiet ac, pretium eu elit. Phasellus sit amet sapien a lorem rhoncus maximus. Fusce viverra pulvinar fermentum. Sed quis ultrices velit, ac mollis dolor. Maecenas porttitor tellus vitae sem consectetur, eget vehicula lacus convallis. Nam eget pulvinar mauris. Donec gravida sem eget rutrum euismod.
</p>
<p>
Nam ut ipsum eget felis ornare vehicula. Quisque gravida, turpis quis tempus faucibus, neque ipsum pretium metus, sit amet porttitor tellus augue a tortor. Duis id justo efficitur, maximus arcu pulvinar, interdum risus. Sed posuere augue ut blandit hendrerit. Vivamus sit amet malesuada tellus, eu pulvinar orci. Aliquam sit amet bibendum metus. Duis scelerisque velit eu cursus venenatis. Etiam finibus porta nibh, ut pharetra augue.
</p>
<p>
Nulla feugiat non nisi et interdum. Morbi imperdiet volutpat odio quis tempor. Proin lobortis pretium sem, venenatis tincidunt tellus pellentesque a. Suspendisse sit amet massa pharetra, fermentum enim at, consectetur quam. In sodales varius nibh, semper ullamcorper erat accumsan a. Phasellus non dui eleifend, ultrices urna vel, scelerisque massa. Nulla vitae feugiat arcu. Phasellus sit amet risus cursus, accumsan dui ut, tincidunt libero. Praesent elementum lobortis nulla quis laoreet. Ut cursus mollis mauris, id fringilla enim accumsan ut. Nullam eget tortor in lacus sollicitudin interdum. Praesent at ultrices justo. In viverra sollicitudin metus. Cras dapibus dolor vitae erat suscipit feugiat.
</p>
<p>
Vestibulum nec est dictum, ornare ante ut, posuere diam. Quisque ac porta urna. In porttitor ex a sapien accumsan iaculis. Donec sed purus aliquet est tempus ultrices. Nam vehicula ut turpis consequat hendrerit. Nulla viverra tortor ac ex efficitur placerat. Sed pretium condimentum aliquam. Phasellus ornare nec justo et dignissim. Nullam leo nunc, sagittis in porta vitae, elementum eget quam. Fusce et eros finibus, semper lacus eget, mattis sapien. Sed pulvinar, risus et facilisis fermentum, sem nunc ultrices eros, ut dapibus lectus enim non velit. Sed eget turpis in libero suscipit bibendum quis sit amet ipsum.
</p>
<p>
Vestibulum ac ipsum eros. Phasellus a tincidunt sapien, sed viverra turpis. Aenean maximus vitae enim in congue. Proin tristique neque nibh, ut convallis dolor elementum non. Fusce vestibulum arcu et eros laoreet pulvinar. Sed at ante non nunc efficitur pulvinar a in nulla. Phasellus dapibus orci id nibh lacinia iaculis. Vestibulum metus libero, dapibus ac ultrices ut, maximus at turpis. Ut ac vulputate eros. Fusce suscipit sagittis dolor vitae gravida. Integer bibendum eu quam in tempor.
</p>
<p>
Duis non laoreet tortor, sit amet sagittis augue. Nulla pellentesque nisl sed elementum consectetur. Fusce dapibus enim ac velit efficitur, a feugiat ex bibendum. Etiam lobortis, ipsum ac maximus eleifend, massa dolor semper tortor, ac sagittis nibh est non arcu. Proin eleifend posuere efficitur. Sed turpis justo, dictum commodo dolor ac, ornare varius nisi. Donec sit amet metus eget leo dictum auctor. Cras id arcu commodo felis bibendum porttitor vel porttitor sem. Cras accumsan, ex eget molestie rutrum, sapien ex tincidunt justo, nec laoreet lorem leo vitae nisl.
</p>
<p>
Nam malesuada elit quis turpis congue, eu facilisis massa porta. Ut ultrices consequat sem eu commodo. Integer nec erat luctus, consectetur est et, congue neque. Morbi quis bibendum odio, in ullamcorper velit. Nunc placerat erat tortor, at condimentum felis molestie a. Donec facilisis nulla tincidunt leo tempus, vitae volutpat orci eleifend. Aenean in auctor nunc. Phasellus nec imperdiet sem. In consequat, justo non fermentum feugiat, risus mauris malesuada justo, eget mollis nisl mi non lectus. Phasellus sem ligula, facilisis vel arcu sit amet, ultrices volutpat nibh. Morbi non nunc ultrices, bibendum sem in, ullamcorper nisi. Aliquam mollis arcu et mi fermentum, nec eleifend turpis maximus.
</p>
</div> 
</div> <!-- End of Main Content -->

<div id="push">
</div>

</div> <!-- End of Page -->

<!-- Start of Footer -->
<div id="footer">
    
    <!-- Start of Copyright -->
    <div id="copyright">
        <p>Copyright &copy 2014</p>
    </div> <!-- End of Copyright -->
    
    <!-- Start of Authors -->
    <div id="authors">
        <p>Developed by: Harish, Andres, Dmitri, Christopher</p>
    </div> <!-- End of Authors -->

</div> <!-- End of Footer -->
</body>
</html>
