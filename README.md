# PostOffice 
[**Version 1.0.2**](id:version)

This is a library for easily constructing Holo and Material Design Dialogs.
There are screenshots of my progress in the /images folder as this is a work in progress.

## Usage

Call `PostOffice.newMail()` to start building a new dialog. This method will return a `Delivery` object which is an interface into the actual system Dialog that is created from the builder. 

_OR_ 

Call one of the simpler quick call items;
	
	PostOffice.newAlertMail(Context, Title, Message)
	PostOffice.newAlertMail(Context, Title, Message, AlertHandler)
	PostOffice.newEditTextMail(Context, Title, Hint, InputType, OnTextAcceptedListener)
	PostOffice.newProgressMail(Context, Title, Suffix, Indeterminate);
	PostOffice.newSimpleListMail(Context, Title, Design, Contents[], OnItemAcceptedListener<T>)

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
			.setDesign(Designs.<HOLO|MATERIAL>_<LIGHT|DARK>)
			
			.setStyle(
				new EditTextStyle.Builder(Context)
					.setText(CharSequence)
					.setHint(CharSequence)
					.setTextColor(Integer)
					.setHintColor(Integer)
					.addTextWatcher(TextWatcher)
					.setInputType(Integer)
					.setOnTextAcceptedListener(OnTextAcceptedListener)
					.build
				new ProgressStyle.Builder(Context)
					.setSuffix(String)	
					.setCloseOnFinish(Boolean)
					.setPercentageMode(Boolean)
					.setInterdeterminate(Boolean)
					.build()
				new ListStyle.Builder(Context)
					.setDividerHeight(Float)
					.setDivider(Drawable)
					.setListSelector(<Integer|Drawable>)
					.setDrawSelectorOnTop(Boolean)
					.setFooterDividersEnabled(Boolean)
					.setHeaderDividersEnabled(Boolean)
					.addHeader(View)
					.addHeader(View, Object, Boolean)
					.addFooter(View)
					.addFooter(View, Object, Boolean)
					.setOnItemClickListener(OnItemClickListener)
					.setOnItemLongClickListener(OnItemLongClickListener)
					.setOnItemAcceptedListener(OnItemAcceptedListener<T>)
					.build(BaseAdapter)
			)
			.build();
			
### Delivery Interface

Here is the list of delivery interface methods

	.setOnCancelListener(DialogInterface.OnCancelListener)
	.setOnDismissListener(DialogInterface.OnDismissListener)
	.setOnShowListener(DialogInterface.OnShowListener)
	.getStyle()	
	
	.show(FragmentManager manager, String tag)
	.show(FragmentManager manager)
	.show(FragmentTransaction transaction, String tag)
    .show(FragmentTransaction transaction)
	.dismiss()
	
## Example Usage

	PostOffice.newAlertMail(ctx, R.string.title, R.string.message)
		      .show(getFragmentManager(), null);
		      
or
		      
	PostOffice.newMail(ctx)
			  .setTitle("Some awesome title")
			  .setMessage("Something cool just happened, check it out.")
			  .setIcon(R.drawable.ic_launcher)
			  .setThemeColor(R.color.app_color)
			  .setDesign(Design.MATERIAL_LIGHT)
			  .build()
			  .show(getFragmentManager());
			  
		    
## Screenshots
		    
![Home](images/po_1.png)  ![Home Dark](images/po_8.png)
![Alert Holo](images/po_2.png)  ![Alert Material](images/po_3.png)  
![EditText Material](images/po_4.png)  ![Progress Holo](images/po_5.png)  
![List Holo](images/po_6.png)  ![List Mtrl](images/po_7.png)
	
## Implementing
Add this line to your gradle dependencies

	compile 'com.r0adkll:postoffice:1.0.2'

## Author

-	Drew Heavner - [r0adkll](http://r0adkll.com)

## Attribution

-	[RippleView](https://github.com/siriscac/RippleView) - Muthuramakrishnan - [siriscac@gmail.com](mailto:siriscac@gmail.com)
	-	Used to produce the ripple affect on Material dialogs


## License

-	Apache License 2.0 - [LICENSE](LICENSE.md)
