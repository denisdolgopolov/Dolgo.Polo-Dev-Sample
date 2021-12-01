SpannableStringBuilder builder = new SpannableStringBuilder();

SpannableString str1 = new SpannableString("Регистрируясь, вы принимаете ");

SpannableString str2 = new SpannableString("политику конфиденциальности");
str2.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://dick/policy"));
                startActivity(browserIntent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
  }, 0, str2.length(), 0);


SpannableString str3 = new SpannableString("согласие на обработку персональных данных"));
str3.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://dick/agree"));
                startActivity(browserIntent);
            }
  }, 0, str3.length(), 0);


builder.append(str1);
builder.append(" ");
builder.append(str2);
builder.append(" и ");
builder.append(str3);

textView.setText(builder, TextView.BufferType.SPANNABLE);
textView.setMovementMethod(LinkMovementMethod.getInstance());
textView.setLinkTextColor(R.color.blue);