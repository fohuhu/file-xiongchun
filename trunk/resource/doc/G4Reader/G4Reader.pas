unit G4Reader;

interface

uses
  Windows, ShellAPI, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, Menus, OleCtrls, AcroPDFLib_TLB, AppEvnts;

type
  TMainForm = class(TForm)
    myPDF: TAcroPDF;
    MainMenu1: TMainMenu;
    F1: TMenuItem;
    H1: TMenuItem;
    N1: TMenuItem;
    W1: TMenuItem;
    U1: TMenuItem;
    A1: TMenuItem;
    procedure Q1Click(Sender: TObject);
    procedure FormCreate(Sender: TObject);
    procedure N1Click(Sender: TObject);
    procedure W1Click(Sender: TObject);
    procedure U1Click(Sender: TObject);
    procedure ApplicationEvents1Message(var Msg: tagMSG;
      var Handled: Boolean);
  private
    { Private declarations }

  public
    { Public declarations }
  end;

var
  MainForm: TMainForm;

implementation

{$R *.dfm}

procedure TMainForm.Q1Click(Sender: TObject);
begin
  close();
end;

procedure TMainForm.FormCreate(Sender: TObject);
begin
  myPdf.LoadFile('G4Studio¼¼Êõ°×Æ¤Êé.pdf');
  myPdf.Width := MainForm.Width - 20;
  myPdf.Height := MainForm.Height - 58;
  myPdf.setShowToolbar(false);
end;

procedure TMainForm.N1Click(Sender: TObject);
begin
  myPdf.Free();
  close();
end;

procedure TMainForm.W1Click(Sender: TObject);
begin
 ShellExecute(Handle, 'open', 'Explorer.exe', 'http://www.g4studio.org', nil, SW_SHOWNORMAL);
end;

procedure TMainForm.U1Click(Sender: TObject);
begin
  ShellExecute(Handle, 'open', 'Explorer.exe', 'http://user.qzone.qq.com/307916217/blog/1318988569', nil, SW_SHOWNORMAL);
end;

procedure TMainForm.ApplicationEvents1Message(var Msg: tagMSG;
  var Handled: Boolean);
  
var mPoint : TPoint;
begin
   if
     (Msg.message = WM_RBUTTONDOWN)and(Msg.hwnd=myPdf.Handle)
   then
     //GetCursorPos(mPoint);
     //PopupMenu1.Popup(mPoint.X, mPoint.Y);
     //Handled:=True;
end;

end.
