; Set input focus to the edit control of Upload window using the handle returned by WinWait

  ControlFocus("File Upload","","Edit1")

  Sleep(2000)

; Set the File name text on the Edit field

  ControlSetText("File Upload", "", "Edit1", "C:\Users\LAKSHMAN\Desktop\New_L\IMG_20160511_183214327.jpg")

  Sleep(2000)

; Click on the Open button

  ControlClick("File Upload", "","Button1");