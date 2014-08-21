PostOffice
==========

### !!! WORK IN PROGRESS !!!

This is a library for easily constructing Holo and Material Design Dialogs.
There are screenshots of my progress in the /images folder as this is a work in progress.

## Usage

Call `PostOffice.newMail()` to start building a new dialog. This method will return a `Delivery` object which is an interface into the actual system Dialog that is created from the builder. 

### Mail Interface

	Delivery interface = PostOffice.newMail(Context)
			.setTitle(<CharSequence|Integer>)
			.setMessage(<CharSequence|Integer>)
			.setIcon(Integer)
			
			.setButton(Integer, <CharSequence, Integer>, DialogInterface.OnClickListener)
			.setThemeColor(int color)
			
			.showKeyboardOnDisplay(Boolean)
			.setCancelable(Boolean)
			.setCanceledOnTouchOutside(Boolean)
			.setDesign(Designs.<HOLO|MATERIAL|CUSTOM>)
			
			.setStyle(
				EditTextStyle,			// Dialog with input field content
					.setText(CharSequence)
					.setHint(CharSequence)
					.setTextColor(Integer)
					.setHintColor(Integer)
					.addTextWatcher(TextWatcher)
					.setInputType(Integer)
				ProgressStyle,			// Progress Dialog with a title, read/total, percentage
					.setProgress(Integer)
					.setTotal(Integer)
					.setInterdeterminate(Boolean)
				ListStyle,				// Dialog with list content TODO
					.setAdapter(ListAdapter)
					.setDividerHeight(Float)
					.setDivider(Integer|Drawable)
					.setListSelector(Integer)
					.setDrawSelectorOnTop(Boolean)
				WebViewStyle,			// Dialog with WebView content
					.setUrl(String)
				CustomStyle				// Dialog with custom view content (this may or may not be useful)
					.setContentView(View);
			)
			.build();
			
### Delivery Interface

Here is the list of delivery interface methods

	.setOnCancelListener(DialogInterface.OnCancelListener)
	.setOnDismissListener(DialogInterface.OnDismissListener)
	.setOnShowListener(DialogInterface.OnShowListener)
	.getStyle()	
	
	.show(FragmentManager manager, String tag)            // Create DialogFragment instance
	.show(FragmentTransaction transaction, String tag)    // Create DialogFragment instance
	.show()									               // Create AlertDialog instance
	.dismiss()
	
## Implementing
**[COMING SOON]**  
Add this line to your gradle dependencies

	compile 'com.r0adkll:postoffice:+'

## Author

-	Drew Heavner - [r0adkll](http://r0adkll.com)

## License

-	Apache License 2.0 - [LICENSE](LICENSE.md)