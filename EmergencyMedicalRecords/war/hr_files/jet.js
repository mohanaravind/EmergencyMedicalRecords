window.onload = function __loadJet()
{
	__counterJet();

/*
	if (__jet_config.tracking !== false)
	{
		var links = document.getElementsByTagName("a");
		var len = links.length;
		for	(var i=0; i<len; i++)
		{
			var value = links[i];
			__addJetEvent(value, 'click', function() { __clickJetTrack(this) });
		}
	}
*/
 };
 

function __counterJet()
{
	var ref = document.domain; 
	if (ref.substring(0,4)=="www.") ref = ref.substring(4,ref.length);
	
	var cookieEnabled=(navigator.cookieEnabled)? true : false;
	if (typeof navigator.cookieEnabled=="undefined" && !cookieEnabled)
	{ 
		document.cookie="x";
		cookieEnabled = (document.cookie.indexOf("x")!=-1)? true : false;
	}	

	if (cookieEnabled) var avic = 1;
	else var avic = 0;	
	
	var user_visit, user_unique;
	
	var expires = new Date(); 
	expires.setTime(expires.getTime() + 3153600000000); 
	
	var today = new Date();
	var expires2 = new Date(today.getFullYear(),today.getMonth(),today.getDate(),23,59,59); 
	
	if (__getJetCookie(ref+"_do")) user_visit = 0;
	else
	{ 
		document.cookie = ref+"_do=1;domain="+ref+";expires=" + expires2.toGMTString() + ";path=/";
		user_visit = 1;
	}
	
	if(__getJetCookie(ref+"_at")) 
	{
		user_unique = 0;
		var hash = __getJetCookie(ref+"_at");		
	}
	else
	{		
		var hash = md5(new Date().getTime() + navigator.userAgent);
		document.cookie = ref+"_at="+hash+";domain="+ref+"; expires=" + expires.toGMTString() + ";path=/";
		user_unique = 1;
	}
		
	var avit = escape(encodeURIComponent(document.title));
	var referrer = escape(encodeURIComponent(document.referrer));
	var location = escape(encodeURIComponent(document.location));
	var lang = (navigator.language) ? navigator.language : navigator.userLanguage; 
	var anchor = __jetUrl();
		
	(new Image()).src='http://jet.imperavi.com/track.co?uid='+__jet_config.uid+'&domain='+__jet_config.domain+'&site='+ref+'&a=' + navigator.userAgent + '&hash='+hash+'&lang='+lang+'&user_visit='+user_visit+'&user_unique='+user_unique+'&anchor='+anchor+'&c='+avic+'&s='+screen.width+'x'+screen.height+'&g='+avit+'&r='+referrer+'&p='+location + '&timestamp=' + new Date().getTime();
}

function __jetUrl()
{
	return escape(encodeURIComponent(window.location.hash.substring(1)));	
}

function __clickJetTrack(element)
{
	var ref = document.domain; 
	if (ref.substring(0,4)=="www.") ref = ref.substring(4,ref.length);

	var r_href = element.getAttribute('href');
	var href = escape(encodeURIComponent(r_href));
	if (href == 'null') href = '';

	element.setAttribute('href', 'javascript:void(null);');

	var id = element.getAttribute('id');
	if (id == null) id = '';	
	
	var className = element.getAttribute('className');
	if (className == null) className = '';
		
	var rel = escape(encodeURIComponent(element.getAttribute('rel')));			
	if (rel == 'null') rel = '';	

	var text = escape(encodeURIComponent(element.innerHTML));			 
	if (text == 'null') text = '';	
	
	var location = escape(encodeURIComponent(document.location));
	var hash = __getJetCookie(ref+"_at");	
	
    if(document.images)
    { 
        (new Image()).src='http://jet.lessio.ru/clicks.php?uid='+__jet_config.uid+'&domain='+__jet_config.domain+'&site=' + ref + '&hash=' +hash + '&p='+location+'&class=' + className + '&rel=' + rel + '&id=' + id + '&text=' + text + '&href=' + href; 
	} 
	
	setTimeout(function()
	{
		element.setAttribute('href', r_href);
		if (r_href.search('javascript') == -1) top.location.href = r_href;
	}, 100);

    return true;	
}

function __addJetEvent(obj, type, fn )
{
	if (obj.addEventListener)
		obj.addEventListener( type, fn, false );
	else if (obj.attachEvent)
	{
		obj["e"+type+fn] = fn;
		obj[type+fn] = function() { obj["e"+type+fn]( window.event ); }
		obj.attachEvent( "on"+type, obj[type+fn] );
	}

}


function __getJetCookie(name) {
	var matches = document.cookie.match(new RegExp( "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"));
	return matches ? decodeURIComponent(matches[1]) : false 
}



//jet_api.set('404');


var jet_api = {
	set: function(name)
	{
		this.save(name);
	},
	save: function(name)
	{
		var ref = document.domain; 
		if (ref.substring(0,4)=="www.") ref = ref.substring(4,ref.length);	
		var location = escape(encodeURIComponent(document.location));	
		var hash = __getJetCookie(ref+"_at");
	
	    if(document.images)
	    { 
	        (new Image()).src='http://jet.lessio.ru/api.php?uid='+__jet_config.uid+'&domain='+__jet_config.domain+'&site=' + ref + '&hash=' + hash + '&p='+location+'&name=' + name; 
		} 	
	}
};




		
function md5(str) {

	str = new String(str);

	var hexcase = 0;  /* hex output format. 0 - lowercase; 1 - uppercase        */
	var b64pad  = ""; /* base-64 pad character. "=" for strict RFC compliance   */
	var chrsz   = 8;  /* bits per input character. 8 - ASCII; 16 - Unicode      */

	var len = str.length * chrsz;
	
	var x = Array();
  	var mask = (1 << chrsz) - 1;
  	for(var i = 0; i < len; i += chrsz)
	  {
    	x[i>>5] |= (str.charCodeAt(i / chrsz) & mask) << (i%32);
	  }	
		
	/* append padding */
  	x[len >> 5] |= 0x80 << ((len) % 32);
  	x[(((len + 64) >>> 9) << 4) + 14] = len;

  	var a =  1732584193;
  	var b = -271733879;
  	var c = -1732584194;
  	var d =  271733878;

  	for(var i = 0; i < x.length; i += 16)
  	{
    	var olda = a;
    	var oldb = b;
    	var oldc = c;
    	var oldd = d;

	    a = md5_ff(a, b, c, d, x[i+ 0], 7 , -680876936);
	    d = md5_ff(d, a, b, c, x[i+ 1], 12, -389564586);
	    c = md5_ff(c, d, a, b, x[i+ 2], 17,  606105819);
	    b = md5_ff(b, c, d, a, x[i+ 3], 22, -1044525330);
	    a = md5_ff(a, b, c, d, x[i+ 4], 7 , -176418897);
	    d = md5_ff(d, a, b, c, x[i+ 5], 12,  1200080426);
	    c = md5_ff(c, d, a, b, x[i+ 6], 17, -1473231341);
	    b = md5_ff(b, c, d, a, x[i+ 7], 22, -45705983);
	    a = md5_ff(a, b, c, d, x[i+ 8], 7 ,  1770035416);
	    d = md5_ff(d, a, b, c, x[i+ 9], 12, -1958414417);
	    c = md5_ff(c, d, a, b, x[i+10], 17, -42063);
	    b = md5_ff(b, c, d, a, x[i+11], 22, -1990404162);
	    a = md5_ff(a, b, c, d, x[i+12], 7 ,  1804603682);
	    d = md5_ff(d, a, b, c, x[i+13], 12, -40341101);
	    c = md5_ff(c, d, a, b, x[i+14], 17, -1502002290);
	    b = md5_ff(b, c, d, a, x[i+15], 22,  1236535329);
	
	    a = md5_gg(a, b, c, d, x[i+ 1], 5 , -165796510);
	    d = md5_gg(d, a, b, c, x[i+ 6], 9 , -1069501632);
	    c = md5_gg(c, d, a, b, x[i+11], 14,  643717713);
	    b = md5_gg(b, c, d, a, x[i+ 0], 20, -373897302);
	    a = md5_gg(a, b, c, d, x[i+ 5], 5 , -701558691);
	    d = md5_gg(d, a, b, c, x[i+10], 9 ,  38016083);
	    c = md5_gg(c, d, a, b, x[i+15], 14, -660478335);
	    b = md5_gg(b, c, d, a, x[i+ 4], 20, -405537848);
	    a = md5_gg(a, b, c, d, x[i+ 9], 5 ,  568446438);
	    d = md5_gg(d, a, b, c, x[i+14], 9 , -1019803690);
	    c = md5_gg(c, d, a, b, x[i+ 3], 14, -187363961);
	    b = md5_gg(b, c, d, a, x[i+ 8], 20,  1163531501);
	    a = md5_gg(a, b, c, d, x[i+13], 5 , -1444681467);
	    d = md5_gg(d, a, b, c, x[i+ 2], 9 , -51403784);
	    c = md5_gg(c, d, a, b, x[i+ 7], 14,  1735328473);
	    b = md5_gg(b, c, d, a, x[i+12], 20, -1926607734);
	
	    a = md5_hh(a, b, c, d, x[i+ 5], 4 , -378558);
	    d = md5_hh(d, a, b, c, x[i+ 8], 11, -2022574463);
	    c = md5_hh(c, d, a, b, x[i+11], 16,  1839030562);
	    b = md5_hh(b, c, d, a, x[i+14], 23, -35309556);
	    a = md5_hh(a, b, c, d, x[i+ 1], 4 , -1530992060);
	    d = md5_hh(d, a, b, c, x[i+ 4], 11,  1272893353);
	    c = md5_hh(c, d, a, b, x[i+ 7], 16, -155497632);
	    b = md5_hh(b, c, d, a, x[i+10], 23, -1094730640);
	    a = md5_hh(a, b, c, d, x[i+13], 4 ,  681279174);
	    d = md5_hh(d, a, b, c, x[i+ 0], 11, -358537222);
	    c = md5_hh(c, d, a, b, x[i+ 3], 16, -722521979);
	    b = md5_hh(b, c, d, a, x[i+ 6], 23,  76029189);
	    a = md5_hh(a, b, c, d, x[i+ 9], 4 , -640364487);
	    d = md5_hh(d, a, b, c, x[i+12], 11, -421815835);
	    c = md5_hh(c, d, a, b, x[i+15], 16,  530742520);
	    b = md5_hh(b, c, d, a, x[i+ 2], 23, -995338651);
	
	    a = md5_ii(a, b, c, d, x[i+ 0], 6 , -198630844);
	    d = md5_ii(d, a, b, c, x[i+ 7], 10,  1126891415);
	    c = md5_ii(c, d, a, b, x[i+14], 15, -1416354905);
	    b = md5_ii(b, c, d, a, x[i+ 5], 21, -57434055);
	    a = md5_ii(a, b, c, d, x[i+12], 6 ,  1700485571);
	    d = md5_ii(d, a, b, c, x[i+ 3], 10, -1894986606);
	    c = md5_ii(c, d, a, b, x[i+10], 15, -1051523);
	    b = md5_ii(b, c, d, a, x[i+ 1], 21, -2054922799);
	    a = md5_ii(a, b, c, d, x[i+ 8], 6 ,  1873313359);
	    d = md5_ii(d, a, b, c, x[i+15], 10, -30611744);
	    c = md5_ii(c, d, a, b, x[i+ 6], 15, -1560198380);
	    b = md5_ii(b, c, d, a, x[i+13], 21,  1309151649);
	    a = md5_ii(a, b, c, d, x[i+ 4], 6 , -145523070);
	    d = md5_ii(d, a, b, c, x[i+11], 10, -1120210379);
	    c = md5_ii(c, d, a, b, x[i+ 2], 15,  718787259);
	    b = md5_ii(b, c, d, a, x[i+ 9], 21, -343485551);
	
	    a = safe_add(a, olda);
	    b = safe_add(b, oldb);
	    c = safe_add(c, oldc);
	    d = safe_add(d, oldd);
	}
  
  	var binarray = Array(a, b, c, d);
  	var hex_tab = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
  	var str = "";
  	for(var i = 0; i < binarray.length * 4; i++)
  	{
    	str += hex_tab.charAt((binarray[i>>2] >> ((i%4)*8+4)) & 0xF) + hex_tab.charAt((binarray[i>>2] >> ((i%4)*8  )) & 0xF);
  	}
  	
	return str;
  
	/*
	 * Add integers, wrapping at 2^32. This uses 16-bit operations internally
	 * to work around bugs in some JS interpreters.
	 */
	function safe_add(x, y)
	{
	  var lsw = (x & 0xFFFF) + (y & 0xFFFF);
	  var msw = (x >> 16) + (y >> 16) + (lsw >> 16);
	  return (msw << 16) | (lsw & 0xFFFF);
	}
	
	/*
	 * Bitwise rotate a 32-bit number to the left.
	 */
	function bit_rol(num, cnt)
	{
	  return (num << cnt) | (num >>> (32 - cnt));
	}
	
	/*
	 * These functions implement the four basic operations the algorithm uses.
	 */
	function md5_cmn(q, a, b, x, s, t)
	{
	  return safe_add(bit_rol(safe_add(safe_add(a, q), safe_add(x, t)), s),b);
	}
	function md5_ff(a, b, c, d, x, s, t)
	{
	  return md5_cmn((b & c) | ((~b) & d), a, b, x, s, t);
	}
	function md5_gg(a, b, c, d, x, s, t)
	{
	  return md5_cmn((b & d) | (c & (~d)), a, b, x, s, t);
	}
	function md5_hh(a, b, c, d, x, s, t)
	{
	  return md5_cmn(b ^ c ^ d, a, b, x, s, t);
	}
	function md5_ii(a, b, c, d, x, s, t)
	{
	  return md5_cmn(c ^ (b | (~d)), a, b, x, s, t);
	}

}


