/* (c) 2011 Infosys Technologies Limited, Bangalore, India. All rights reserved.
* Version: 1.0
* Except for any open source software components embedded in this Infosys
* proprietary software program, this Program is protected by
* copyright laws, international treaties and other pending or existing
* intellectual property rights in India, the United States and other countries.
* Except as expressly permitted, any unauthorized reproduction, storage,
* transmission in any form or by any means (including without limitation
* electronic, mechanical, printing, photocopying, recording or otherwise),
* or any distribution of this Program, or any portion of it, may result in 
* severe civil and criminal penalties, and will be prosecuted to the maximum
* extent possible under the law.*/


function aham(parameters) {
	makeAjaxCall(parameters);

}
function aham_getRecommendations(recommendationType,parameters){
	makeAjaxServiceCall(recommendationType,parameters);
}
var context = "";
var productPageUrl = "";
var warLocation = ""; 

function makeAjaxServiceCall(recommendationType,parameters){
	var results=0;
    warLocation = parameters.ahamServicesUrl;
    if(typeof parameters.numberOfResults =="undefined"){
    	results=10;
    }else{
    	results=parameters.numberOfResults;
    }
    var urlString='';
    if(recommendationType=="alsoViewedItems"){
    	 var productId= parameters.productId;
       urlString=warLocation+'/aham/Products.json/'+productId+'/recommendations/alsoViewedItems?mode=Full&limit='+results;
   }else if(recommendationType=="ultimatelyBoughtItems"){
	   var productId= parameters.productId;
	   urlString=warLocation+'/aham/Products.json/'+productId+'/recommendations/ultimatelyBoughtItems?mode=Full&limit='+results;
   }else if(recommendationType=="boughtTogetherItems"){
	   var productId= parameters.productId;
       urlString=warLocation+'/aham/Products.json/'+productId+'/recommendations/boughtTogetherItems?mode=Full&limit='+results;
   }else if(recommendationType=="userCategoryRecommendations"){
	   var productId= parameters.productId;
       urlString=warLocation+'/aham/Products.json/'+productId+'/recommendations/topSellingItems?mode=Full&limit='+results;
   }else if(recommendationType=="smartAssociations"){
	   var productId= parameters.productId;
       urlString=warLocation+'/aham/Products.json/recommendations/smartAssociations/'+parameters.partNumber+'.'+parameters.format;
   }else if(recommendationType=="browsedItems"){
       urlString=warLocation+'/aham/user/browsedItems/'+parameters.userId+'.'+parameters.format;
   }else if(recommendationType=="purchasedItems"){
       urlString=warLocation+'/aham/Users.json/'+parameters.userId+'/purchasedItems?mode=Full';
   }else if(recommendationType=="cartItems"){
       urlString=warLocation+'/aham/Users.json/cartItems/'+parameters.userId+'.'+parameters.format;
   }else if(recommendationType=="peopleBoughtTogether"){
       urlString=warLocation+'/aham/Users.json/peopleBoughtTogether/'+parameters.userId+'.'+parameters.format;
   }else if(recommendationType=="userRecommendations"){
	   urlString=warLocation+'/aham/Users.json/'+parameters.userId+'/strategies/S1/recommendations?mode=Full';
   }else if(recommendationType=="clusterRecommendations"){
       urlString=warLocation+'/aham/Users.json/cluster/recommendations/'+parameters.clusterId+'.'+parameters.format;
   }else if(recommendationType=="searchUltimatelyBought"){
       urlString=warLocation+'/aham/Search.json/'+parameters.searchKey+'/ultimatelyBoughtItems?mode=Full';
   }else if(recommendationType=="facebookRecommendations"){
       urlString=warLocation+'/aham/social/Users.json/facebook/recommendedItems/'+parameters.userId+'/'+parameters.partNumbers+'.'+parameters.format;
   }else if(recommendationType=="facebookCategoryRecommendations"){
       urlString=warLocation+'/aham/social/Users.json/facebook/categoryRecommendedItems/'+parameters.category+'/'+parameters.userId+'.'+parameters.format;
   }else if(recommendationType=="productCarousels"){
       urlString=warLocation+'/aham/wrapper/productCarousels/'+parameters.partNumber+'.'+parameters.format;
   }else if(recommendationType=="userCarousels"){
       urlString=warLocation+'/aham/wrapper/userCarousels/'+parameters.userId+'.'+parameters.format;
   }else if(recommendationType=="CRMCarousels"){
       urlString=warLocation+'/aham/wrapper/CRMCarousels/'+parameters.category+'/'+parameters.userId+'.'+parameters.format;
   }else if(recommendationType=="FBCarousels"){
       urlString=warLocation+'/aham/wrapper/FBCarousels/'+parameters.partNumbers+'/'+parameters.userId+'.'+parameters.format;
   }else if(recommendationType=="facebookRecommendationsOffline"){
       urlString=warLocation+'/aham/social/Users.json/facebook/offline/recommendedItems/'+parameters.userId+'.'+parameters.format;
   }else if(recommendationType=="similarProducts"){
	   var productId= parameters.productId;
	   urlString=warLocation+'/aham/Products.json/'+productId+'/recommendations/similarProducts?mode=Full&limit='+results;
   }else if(recommendationType=="guestRecommendations"){
	   urlString=warLocation+'/aham/Users.json/'+parameters.userId+'/'+parameters.count+'/intentBasedRecommendations?mode=full';
   }
   else if(recommendationType=="getUserRecent"){
       urlString=warLocation+'/aham/wrapper/userCarousels/'+parameters.userId+'.'+parameters.format;
   } else if (recommendationType=="newProductRecommendations"){
	   urlString = warLocation+'/aham/Users.json/'+parameters.userId+'/strategies/S2/recommendations?mode=Full&limit='+results;
   }else if (recommendationType=="suggestedCategoryItems"){
	   urlString = warLocation+'/aham/Users.json/'+parameters.userId+'/strategies/S5/recommendations?mode=Full&limit='+results;
   }else if (recommendationType=="registeredSuggestedItems"){
	   var productId= parameters.productId;
	   urlString = warLocation+'/aham/Users.json/'+parameters.userId+'/registeredUsers/suggestedItems?productId='+productId+'&mode=Full&limit='+results;
   }else if(recommendationType=="visualSimilarItems"){
	   var productId= parameters.productId;
	   urlString=warLocation+'/aham/Products.json/'+productId+'/visualSimilarItems?mode=Full&limit='+results;
   }else if(recommendationType=="productAnalytics"){
	   urlString=warLocation+'/aham/Products.json/041A0051000/productAnalytics?mode=Full';
   }
   
    context = parameters.context;
    productPageUrl = context+"/store/productPage.htm?identifier=";

	//added null/empty check on urlString to make sure ajax call to same page doesnt happen
	if(urlString != null && urlString.length > 0)
	    $.ajax({
	        type:'GET',
	        dataType:'json',
	        failure:ajaxFailure,
	        success:function (data) {
	        	 if(recommendationType=="productAnalytics"){
			//	setUpGraph(data,parameters);
			}else {
				constructCarousel(data, parameters,recommendationType);
			       //    constructCarousel(data,parameters);
			}
	        },
	        url:urlString,
	        error:function ()	{
	            ajaxFailure(parameters);
	        }
	    });
    
	    if(recommendationType=="getGuestBrowsed" ){
	        urlString=warLocation+'/aham/Users.json/'+parameters.userId+'/guest/browsed?mode=Full';
	        $.ajax({
		        type:'GET',
		        dataType:'json',
		        failure:ajaxFailure,
		        success:function (data) {
		        	constructCarousel(data,parameters,recommendationType);
		        },
		        url:urlString,
		        error:function ()	{
		            ajaxFailure(parameters);
		        }
		    });
	    }
	    
	    if(recommendationType=="getGuestRecommendations" ){
	    	 urlString=warLocation+'/aham/Users.json/'+parameters.userId+'/strategies/S8/recommendations?mode=Full&limit='+results;
	    	 $.ajax({
	 	        type:'GET',
	 	        dataType:'json',
	 	        failure:ajaxFailure,
	 	        success:function (data) {
	 	        	constructCarousel(data,parameters,recommendationType);
	 	        },
	 	        url:urlString,
	 	        error:function ()	{
	 	            ajaxFailure(parameters);
	 	        }
	 	    });
	    }
	   
}

function ajaxFailure(parameters){
    //alert("reached in failure::"+recommendationType);
}
function nextPageTest(recommendationType,total,local,carouselType,displayPrice,noOfProPerCar){
	var localDef=true;
	if(typeof noOfProPerCar=="undefined" || noOfProPerCar=="undefined"){
		noOfProPerCar=5;
	}
	if(typeof local=="undefined" || local==0){
		local=0;
		localDef= true;
	}
	var leftPos =0;
	if(carouselType=="vertical" && displayPrice=='true'){
		leftPos=(noOfProPerCar-1)*201;
	}else if (carouselType=="vertical" ){
		leftPos = (noOfProPerCar-1)*186;
	}
	else{
			leftPos=(noOfProPerCar-1)*180;
	}
	if(carouselType=="vertical"){
	/*	if((local)==(noOfProPerCar-1)){
			leftPos = leftPos;
		}else{
		leftPos = leftPos+5;
		}
	*/	
		/*if((local+1)==(noOfProPerCar-1)){
			leftPos = leftPos;
		}else{
		leftPos = leftPos+5;
		}*/
	}
	var currentPos = 0;
	if(carouselType=='vertical'){
		currentPos=document.getElementById(recommendationType.id).style.top;
		}else{
			currentPos=document.getElementById(recommendationType.id).style.left;
		}
	if(currentPos!=""){
	currentPos = currentPos.split("px")[0];
	leftPos = -parseInt(currentPos)+leftPos;
	}
	if(localDef==true){
		local++;
		}
	if(((local)*(noOfProPerCar-1)>=total)){
		if(carouselType == 'vertical'  && displayPrice == 'true'){
		leftPos = leftPos-200;
		}else if(carouselType == 'vertical'){
		leftPos = leftPos-185;
		}
	}
		if(carouselType=='vertical'){
		$("#"+recommendationType.id).animate({top:-leftPos},1000,function() {
			$("#"+recommendationType.id).css({top:-leftPos+'px'});
			});
	}else{
	$("#"+recommendationType.id).animate({left:-leftPos},1000,function() {
		$("#"+recommendationType.id).css({left:-leftPos+'px'});
		});
}
		//((local)*(noOfProPerCar)>=total) &&
	if( (leftPos>=((total*185)-(noOfProPerCar*185)))){
		if(carouselType=='vertical'){
			var next="#"+recommendationType.id+"downPage";
			$(next).addClass("downPage disabled").attr("onClick","");
		}else{
		var next="#"+recommendationType.id+"nextPage";
		$(next).addClass("nextPage disabled").attr("onClick","");
		}
	}else{
		if(carouselType=='vertical'){
			$("#"+recommendationType.id+"downPage").removeClass("downPage disabled").addClass("downPage").attr("onClick","nextPageTest(" +
					""+recommendationType.id+","+total+","+local+",'"+carouselType+"','"+displayPrice+"','"+noOfProPerCar+"')");
		}else{
			$("#"+recommendationType.id+"nextPage").addClass("nextPage").attr("onClick","nextPageTest(" +
					""+recommendationType.id+","+total+","+local+",'"+carouselType+"','"+displayPrice+"','"+noOfProPerCar+"')");	
		}
		
	}
	if(carouselType=='vertical'){
		$("#"+recommendationType.id+"upPage").removeClass("upPage disabled").addClass("upPage").attr("onClick","prevPageTest(" +
				""+recommendationType.id+","+total+","+local+",'"+carouselType+"','"+displayPrice+"','"+noOfProPerCar+"')");
	}else{
	$("#"+recommendationType.id+"prevPage").removeClass("prevPage disabled").addClass("prevPage").attr("onClick","prevPageTest("
			+recommendationType.id+","+total+","+local+",'"+carouselType+"','"+displayPrice+"','"+noOfProPerCar+"')");
	}
	
}
function prevPageTest(recommendationType,total,local,carouselType,displayPrice,noOfProPerCar){
	if(typeof noOfProPerCar=="undefined" || noOfProPerCar=="undefined"){
		noOfProPerCar=5;
	}
	if(typeof local=="undefined" || local==0){
		local=2
	}else{
		local--;
	}
	var rightPos =0;
	if(carouselType=="vertical" && displayPrice=='true'){
		rightPos=(noOfProPerCar-1)*201;
	}else if (carouselType=="vertical"){
		rightPos = (noOfProPerCar-1)*186;
	}
	else{
			rightPos=(noOfProPerCar-1)*180;
	}
	if(carouselType=="vertical"){
		
		/*if((local+1)==(noOfProPerCar-1)){
			rightPos = rightPos;
		}else{
			rightPos = rightPos+5;
		}*/
			/*if(((local+1)*(noOfProPerCar-1))>=total){
			rightPos = rightPos+5;
		}*/
	}
	var totalPos=0 
	if(carouselType=='vertical'){
	 totalPos=document.getElementById(recommendationType.id).style.top;
	}else{
		totalPos=document.getElementById(recommendationType.id).style.left;
	}
	totalPos = totalPos.split("px")[0];
	 rightPos=parseInt(totalPos)+rightPos;
	if(rightPos>0)
		{
		rightPos=0;
		}
	if(carouselType=='vertical'){
	$("#"+recommendationType.id).animate({top:rightPos},1000,"linear",function() {
	$("#"+recommendationType.id).css({top:rightPos+'px'});
	});
	}else{
		$("#"+recommendationType.id).animate({left:rightPos},1000,function() {
			$("#"+recommendationType.id).css({left:rightPos+'px'});
			});
	}
	if(rightPos==0){
		if(carouselType=='vertical'){
			$("#"+recommendationType.id+"upPage").addClass("upPage disabled").attr("onClick","");
		}else{
		$("#"+recommendationType.id+"prevPage").addClass("prevPage disabled").attr("onClick","");
		}
	}else{
		if(carouselType=='vertical'){
			$("#"+recommendationType.id+"upPage").addClass("upPage").attr("onClick","prevPageTest("
					+recommendationType.id+","+total+","+local+",'"+carouselType+"','"+displayPrice+"','"+noOfProPerCar+"')");
		}else{
		$("#"+recommendationType.id+"prevPage").addClass("prevPage").attr("onClick","prevPageTest("
				+recommendationType.id+","+total+","+local+",'"+carouselType+"','"+displayPrice+"','"+noOfProPerCar+"')");
		}
	}
	if(carouselType=='vertical'){
		$("#"+recommendationType.id+"downPage").removeClass("downPage disabled").addClass("downPage").attr("onClick","nextPageTest("
				+recommendationType.id+","+total+","+local+",'"+carouselType+"','"+displayPrice+"','"+noOfProPerCar+"')");
	}else{
	$("#"+recommendationType.id+"nextPage").removeClass("nextPage disabled").addClass("nextPage").attr("onClick","nextPageTest("
			+recommendationType.id+","+total+","+local+",'"+carouselType+"','"+displayPrice+"','"+noOfProPerCar+"')");
	}
}
function constructCarousel(jsonData,parameters,recommendationType){
	var htmlSrc="";
	var htmlSrc1="";
    var count = 0;
    var count = 0;
    var imageIdentifier = recommendationType;
	
    htmlSrc=htmlSrc+'<div id="ahamCarouselWrap">';		
    if(parameters.carouselType=='vertical' && parameters.displayPrice=='true'){
    htmlSrc=htmlSrc+'<div class="vertical" style="height: 1090px;">';
    }else if(parameters.carouselType=='vertical'){
    htmlSrc=htmlSrc+'<div class="vertical" style="height: 1010px;">';
    }else{
    htmlSrc=htmlSrc+'<div class="productCarouselWrap" style="width: 965px;">';
    }
    htmlSrc=htmlSrc+'<div class="carousel">';
    htmlSrc=htmlSrc+'<h3 align="center" class="carouselHeading">'+parameters.title+'</h3><br/><br/>';
    if(parameters.carouselType=='vertical'){
    	htmlSrc=htmlSrc+'<a id="'+recommendationType+'upPage" class="upPage disabled" onclick=prevPageTest('
    	+recommendationType+','+jsonData.length+',0,"'+parameters.carouselType+'","'+parameters.displayPrice+'","'+parameters.noOfProPerCar+'") ></a>';
        }else{													
	 htmlSrc=htmlSrc+'<a id="'+recommendationType+'prevPage" class="prevPage disabled" onclick=prevPageTest('
	 +recommendationType+','+jsonData.length+',0,"'+parameters.carouselType+'","'+parameters.displayPrice+'","'+parameters.noOfProPerCar+'") ></a>';
        }
	 if(parameters.carouselType=='vertical'){
	 htmlSrc=htmlSrc+'<div class="verticalScrollable" style="width:245px;">';
	 }else{
		 htmlSrc=htmlSrc+'<div class="productScrollable" >';
	 }
	 if(parameters.carouselType=='vertical'){
		 htmlSrc=htmlSrc+'<ul class="vertical verticalWrapper"  id="'+recommendationType+'">';
	 }else{
	 htmlSrc=htmlSrc+'<div class="items" id="'+recommendationType+'">';
	 }
	 var results =0;
    for(var i=0;i<jsonData.length;i++){
        var obj = jsonData[i];
       
        var aItem=obj;
        if(aItem!=null){
        var prodName=aItem.partName;
        var prodImageUrl=aItem.imgId;
        var percentViewed=aItem.percentViewed;
        var partNumber = aItem.partNumber;
        var productPageUrlNew  = ""; 
        productPageUrlNew = productPageUrl+partNumber;  
       if(parameters.carouselType=='vertical' && parameters.displayPrice=='true'){
    	   htmlSrc1=htmlSrc1+'<li class="item" style="height:195px;" ><a href="'+productPageUrlNew+'"><img title="'+prodName+'" width="130" height="145" id="'+imageIdentifier+count+'"  src="'+prodImageUrl+'"/>'+
	        '<br /><label style="font-size:em;line-height:1;color:#000000;font-weight:bold;">'+prodName+'</label>' ;
    	   if(parameters.displayPrice=='true'){
    		   htmlSrc1=htmlSrc1+'<br /><label style="font-size:em;line-height:1;color:#000000;font-weight:bold;"> $ '+aItem.salePrice+'</label>';
    	   }
    	   htmlSrc1=htmlSrc1+'</a>';
		  htmlSrc1=htmlSrc1+'</li>';
		  
		//  htmlSrc1=htmlSrc1+'<br />';
       }else if(parameters.carouselType=='vertical' ){
    	   htmlSrc1=htmlSrc1+'<li class="item" style="height:180px;" ><a href="'+productPageUrlNew+'"><img title="'+prodName+'" width="130" height="145" id="'+imageIdentifier+count+'"  src="'+prodImageUrl+'"/>'+
	        '<br /><label style="font-size:em;line-height:1;color:#000000;font-weight:bold;">'+prodName+'</label>' ;
   	   if(parameters.displayPrice=='true'){
   		   htmlSrc1=htmlSrc1+'<br /><label style="font-size:em;line-height:1;color:#000000;font-weight:bold;"> $ '+aItem.salePrice+'</label>';
   	   }
   	   htmlSrc1=htmlSrc1+'</a>';
		  htmlSrc1=htmlSrc1+'</li>';
		  
		//  htmlSrc1=htmlSrc1+'<br />';
      }else{
		 htmlSrc1=htmlSrc1+'<div class="itemWrapper" style="widhth:150px"><div class="item"><a href="'+productPageUrlNew+'"><img title="'+prodName+'" width="130" height="140" id="'+imageIdentifier+count+'"  src="'+prodImageUrl+'"/>'+
	        '<label style="font-size:em;line-height:1;color:#000000;font-weight:bold;">'+prodName+'</label>' ;
		 if(parameters.displayPrice=='true'){
  		   htmlSrc1=htmlSrc1+'<br /><label style="font-size:em;line-height:1;color:#000000;font-weight:bold;"> $ '+aItem.salePrice+'</label>';
  	   }
		 htmlSrc1=htmlSrc1+'</a>';
		  htmlSrc1=htmlSrc1+'</div></div>';
       }
        count = count +1;     
       
        if(typeof parameters.numberOfResults == "undefined"){
        	results=10;
        }else{
        	results=parameters.numberOfResults;
        }
        
        if(count>results) {
        	$('#'+recommendationType).html(htmlSrc1);
           break;
        }
        }
	if(recommendationType=="suggestedCategoryItems"){
		$("#categoryItemsLabelId").html(jsonData[0].categoryDesc);
	}
    }
    htmlSrc=htmlSrc+htmlSrc1;
    if(parameters.carouselType=='vertical'){
    htmlSrc=htmlSrc+'</ul><!--items ends -->';
    }else{
    	 htmlSrc=htmlSrc+'</div><!--items ends -->';
    }
    
	 htmlSrc=htmlSrc+'</div><!--scrollable ends -->';
	 htmlSrc=htmlSrc+'<!-- next page -->';
	 if(parameters.carouselType=='vertical'){
		 htmlSrc=htmlSrc+'<a id="'+recommendationType+'downPage"  class="downPage" ';
		  htmlSrc=htmlSrc+'onclick=nextPageTest('+recommendationType+','+jsonData.length+',0,"'+parameters.carouselType+'","'+parameters.displayPrice+'","'+parameters.noOfProPerCar+'") ></a>';
	        }else{
	  htmlSrc=htmlSrc+'<a id="'+recommendationType+'nextPage"  class="nextPage" ';
	  htmlSrc=htmlSrc+'onclick=nextPageTest('+recommendationType+','+jsonData.length+',0,"'+parameters.carouselType+'","'+parameters.displayPrice+'","'+parameters.noOfProPerCar+'") ></a>';
	        }
	  htmlSrc=htmlSrc+'</div><!--carousel ends-->';  
	  htmlSrc=htmlSrc+'</div><!--carouselWrap ends-->';
	  htmlSrc=htmlSrc+'</div>';	 
	  $('#'+recommendationType+'Div').html(htmlSrc);
	  if(parameters.carouselType=='vertical' && parameters.displayPrice=='true'){
		  if(typeof parameters.noOfProPerCar !='undefined'){
			  var height = (parameters.noOfProPerCar*200)+5;
	    	  var margin = height-65 + 'px 0 0 70px';
	  		$('.vertical').css({'height':height+90});
	    		$('.verticalScrollable').css({'height':height});
	          	$('.downPage').css({'margin': margin});
		  }else{
      	$('.verticalScrollable').css({'height':'1005px'});
      	$('.downPage').css({'margin': '940px 0 0 70px'});
		  }
      }else if(parameters.carouselType=='vertical' && typeof parameters.noOfProPerCar !='undefined'){
    	  var height = (parameters.noOfProPerCar*185)+5;
    	  var margin = height-65 + 'px 0 0 70px';
  		$('.vertical').css({'height':height+90});
    		$('.verticalScrollable').css({'height':height});
          	$('.downPage').css({'margin': margin});
      }else if(typeof parameters.noOfProPerCar !='undefined'){
		  var width=(parameters.noOfProPerCar*180)+5;
		   $('#'+recommendationType+'Div .productScrollable').css({'width':width+'px'});
		   var width1=(parameters.noOfProPerCar*200);
		  $('.productCarouselWrap').css({'width':width1+'px'});
	  }
	  
	if(htmlSrc1==""){ 
		$('#'+recommendationType+'Div').css({'display' : 'none'});
	}
	
}

//Add user activity
/* Input parameters:
 * 1. userId : This can be the user id of the logged in user or session id of the guest user
 * 2. eventVal : ADD_TO_CART,ADD_TO_WISHLIST,VERTICAL_VIEW,CATEGORY_VIEW,SUB_CATEGORY_VIEW,DEL_FROM_CART,DEL_FROM_WISHLIST,KEYWORD_SEARCH,LOGIN,LOGOFF,ORDER_CONFIRM,PRODUCT_VIEW,RECO_VIEW,SAVE_FOR_LATER,SOCIAL
 * 3. entityVal : VERTICAL,CATEGORY,SUB_CATEGORY,KEYWORD,PRODUCT,ORDER,USER,STYLE,LIKE,PLUS_ONE
 * 4. valueVal :
 * 5. descVal : 
 * 6. srcVal : 
 * 7. sessionIDVal : */
function aham_trackActivity(eventVal,parameters) {
	var description = getDescription(eventVal, parameters.entityVal, parameters.valueVal,parameters.sessionIDVal);
	description = escape(description);
	$.ajax({
		type : "GET",
		url : parameters.ahamScribeUrl+"/aham/Scribe.json/Users/"
				+ parameters.userId + "/activities?desc=" + description + "&src=" + parameters.srcVal
				+ "&event=" + eventVal + "&entity=" + parameters.entityVal + "&sessionID="
				+ parameters.sessionIDVal + "&value=" + parameters.valueVal,

		success : function(data) {
			// alert("Successfully recorded the activity");
		},
		failure : function(data) {
			// alert("Successfully recorded the activity");
		},
		error : function() {
			// alert("Failed to record the activity");
		}

	});
}
function getDescription(eventVal, entityVal, valueVal,
		sessionIDVal){
	var description = "NA";
	if((eventVal!=null && eventVal!="") 
			&& (valueVal!=null && valueVal!="")){
		if(eventVal=="PRODUCT_VIEW"){
			description = "You have viewed "+valueVal;
			incrementCookie(sessionIDVal);
		}else if (eventVal=="VERTICAL_VIEW"){
			description = "You have browsed "+valueVal+" vertical";
			incrementCookie(sessionIDVal);
		}else if (eventVal=="CATEGORY_VIEW"){
			description = "You have browsed "+valueVal+" category";
			incrementCookie(sessionIDVal);
		}else if (eventVal=="SUB_CATEGORY_VIEW"){
			description = "You have browsed "+valueVal+" subcategory";
			incrementCookie(sessionIDVal);
		}else if(eventVal=="KEYWORD_SEARCH"){
			description = "You have searched for "+valueVal;
		}
		else if(eventVal=="SOCIAL" && (entityVal!=null && entityVal!="")){
			if(entityVal=="LIKE"){
				description = "You have liked "+valueVal;
			}else if (entityVal=="PLUS_ONE"){
				description = "You have plus-oned "+valueVal;
			}			
		}else if(eventVal=="ADD_TO_CART"){
			description = "You have added "+ valueVal +" to cart";
		}else if(eventVal=="ORDER_CONFIRM"){
			description = "You have created a new order "+valueVal;
		}else if(eventVal=="LOGIN"){
			description="User has logged in "+valueVal;
		}else if(eventVal=="LOGOUT"){
			description="User has logged out "+valueVal;
		}
	}		
	return description;
}

var new_value = 0;// global browse count for a user

function createAhamCookie(userId) {

	var check_name = 'aham_store';
	// var a_all_cookies = document.cookie.split(';');
	var checkIndex = document.cookie.indexOf(check_name + "=");
	// creating a new cookie named aham_store
	if (checkIndex < 0) {
		document.cookie = "aham_store=" + '{"user_id":"' + userId
				+ '","browse_count":0}' + "; path=/opencommerce-demosite-web";
	} else {
		var a_all_cookies = document.cookie.split(';');
		// var a_temp_cookie = new Array();
		var cookie_name = '';
		var cookie_value = '';
		var current_value = '';
		var b_cookie_found = false;
		for (i = 0; i < a_all_cookies.length; i++) {
			var a_temp_cookie = new Array();
			// splitting apart each name=value pair
			a_temp_cookie = a_all_cookies[i].split('=');

			// trim left/right whitespace
			cookie_name = a_temp_cookie[0].replace(/^\s+|\s+$/g, '');
			if (cookie_name == check_name) {
				// alert(cookie_name);
				cookie_value = a_temp_cookie[1].replace(/^\s+|\s+$/g, '');
				// var obj=jQuery.parseJSON(cookie_value);
				var obj = JSON.parse(cookie_value);
				// alert(obj.browse_count);
				current_value = parseFloat(obj.browse_count);
				// alert(new_value);
				document.cookie = "aham_store=" + '{"user_id":"' + userId
						+ '","browse_count":"' + current_value + '"}'
						+ "; path=/opencommerce-demosite-web";
				// alert(new_value);
				break;
			}
		}
	}

	// alert(document.cookie );
}

function incrementCookie(sessionIDVal) {
	var check_name = 'aham_store';
	var a_all_cookies = document.cookie.split(';');
	// var a_temp_cookie = new Array();
	var cookie_name = '';
	var cookie_value = '';
	var b_cookie_found = false;
	for (i = 0; i < a_all_cookies.length; i++) {
		var a_temp_cookie = new Array();
		// splitting apart each name=value pair
		a_temp_cookie = a_all_cookies[i].split('=');

		// trim left/right whitespace
		cookie_name = a_temp_cookie[0].replace(/^\s+|\s+$/g, '');
		if (cookie_name == check_name) {
			// alert(cookie_name);
			cookie_value = a_temp_cookie[1].replace(/^\s+|\s+$/g, '');
			// var obj=jQuery.parseJSON(cookie_value);
			var obj = JSON.parse(cookie_value);
			// alert(obj.browse_count);
			if(obj.user_id==sessionIDVal){
				new_value = parseFloat(obj.browse_count) + 1;
			}else{
				new_value=0;
			}
			// alert(new_value);
			document.cookie = "aham_store=" + '{"user_id":"' + obj.user_id
					+ '","browse_count":"' + new_value + '"}'
					+ "; path=/opencommerce-demosite-web";
			// alert(new_value);
			break;
		}
	}
}



