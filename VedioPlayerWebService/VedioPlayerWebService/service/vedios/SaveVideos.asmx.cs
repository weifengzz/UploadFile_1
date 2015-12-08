using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace VedioPlayerWebService.service.vedios
{
    /// <summary>
    /// Upload 的摘要说明。
    /// </summary>
    [WebService(Namespace = "http://xml.sz.luohuedu.net/",
     Description = "在Web Services里利用.NET框架进上载文件。")]
    public class Upload : System.Web.Services.WebService
    {
        public Upload()
        {
            //CODEGEN：该调用是 ASP.NET Web 服务设计器所必需的
            InitializeComponent();
        }

        #region Component Designer generated code

        //Web 服务设计器所必需的
        private IContainer components = null;

        /// <summary>
        /// 设计器支持所需的方法 - 不要使用代码编辑器修改
        /// 此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
        }

        /// <summary>
        /// 清理所有正在使用的资源。
        /// </summary>
        protected override void Dispose(bool disposing)
        {
            if (disposing && components != null)
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #endregion

        [WebMethod(Description = "Web 服务提供的方法，返回是否文件上载成功与否。")]
        public string UploadFile(byte[] fs, string FileName)
        {
            try
            {
                ///定义并实例化一个内存流，以存放提交上来的字节数组。
                MemoryStream m = new MemoryStream(fs);
                ///定义实际文件对象，保存上载的文件。
                FileStream f = new FileStream(Server.MapPath(".") + "\\"
                 + FileName, FileMode.Create);
                ///把内内存里的数据写入物理文件
                m.WriteTo(f);
                m.Close();
                f.Close();
                f = null;
                m = null;
                return "文件已经上传成功。";
            }
            catch (Exception ex)
            {
                return ex.Message;
            }
        }
        [WebMethod(EnableSession = true, Description = "上传文件")]
        public int FileUploadByBase64String(string base64string, string fileName1)
        {
            try
            {
                string fileName = "D:\\edioPlayerWeb\videos\\" + fileName1;
                // 取得文件夹
                string dir = fileName.Substring(0, fileName.LastIndexOf("\\"));
                //如果不存在文件夹，就创建文件夹
                if (!Directory.Exists(dir))
                    Directory.CreateDirectory(dir);
                byte[] bytes = Convert.FromBase64String(base64string);
                MemoryStream memoryStream = new MemoryStream(bytes, 0, bytes.Length);
                memoryStream.Write(bytes, 0, bytes.Length);
                // 写入文件
                File.WriteAllBytes(fileName, memoryStream.ToArray());
                if (File.Exists(fileName))
                {
                    FileStream fsSource = new FileStream(fileName, FileMode.Open, FileAccess.Read);
                    fsSource.Close();
                }
                return 1;
            }
            catch (Exception ex)
            {
                //MyFuncLib.Log(ex.Message + "\r\n" + ex.StackTrace);
                return -1;
            }
        }
    }
}
