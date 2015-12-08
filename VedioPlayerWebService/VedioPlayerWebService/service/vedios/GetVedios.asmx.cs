using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.IO;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.Script.Services;
using System.Web.Services;
using System.Web.Services.Protocols;

namespace VedioPlayerWebService.service.vedios
{
    /// <summary>
    /// sv 的摘要说明
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    [System.Web.Script.Services.ScriptService]
    [SoapDocumentService(RoutingStyle = SoapServiceRoutingStyle.RequestElement)]
    public class GetVedios : System.Web.Services.WebService
    {
        /// <summary>
        /// 查询一个vedio
        /// </summary>
        /// <param name="username"></param>
        /// <param name="password1"></param>
        [WebMethod]
        [ScriptMethod(ResponseFormat = ResponseFormat.Xml)]
        public void GetAVedio(String id)
        {
            SqlParameter[] sp = new SqlParameter[] {
                    new SqlParameter("@id",id)
                };
            string sql = "select * from vedio where vedio_id = @id";
            DataTable dt = SqlHelper.ExecuteDataTable(sql, CommandType.Text, sp);
            string vedioId = String.Empty;
            string vedioTitle = string.Empty;
            string vedioAddress = string.Empty;
            StringBuilder sb = new StringBuilder();
            sb.Append("{\"vedioId\":\"" + dt.Rows[0]["vedio_id"].ToString() + "\",\"vedioTitle\":\"" + dt.Rows[0]["vedio_title"].ToString() + "\",\"vedioAddress\":\"" + Paths.A_VEDIO_ADDRESS + dt.Rows[0]["vedio_address"].ToString() + "\"}");
            String str = sb.ToString();
            HttpContext.Current.Response.Charset = "utf-8";
            HttpContext.Current.Response.Write(sb);

        }
        /// <summary>
        /// 查询一个vedio
        /// </summary>
        /// <param name="username"></param>
        /// <param name="password1"></param>
        [WebMethod]
        [ScriptMethod(ResponseFormat = ResponseFormat.Xml)]
        public void login(String un, String pwd)
        {

            StringBuilder sb = new StringBuilder();
            sb.Append("{\"un\":\"" + "majinxin" + "\",\"vedioTitle\":\"" + "123" + "\"}");
            String str = sb.ToString();
            HttpContext.Current.Response.Charset = "utf-8";
            HttpContext.Current.Response.Write(sb);

        }


        /// <summary>
        /// 这个是上传文件的方法，其余的与这无关
        /// </summary>
        /// <param name="base64string"></param>
        /// <param name="fileName1"></param>
        /// <returns></returns>
        [WebMethod(EnableSession = true, Description = "上传文件")]
        public int FileUploadByBase64String(string base64string, string fileName1)
        {
            try
            {
                string fileName = "D:\\VedioPlayerWeb\\videos\\" + fileName1;
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
                //返回数据如果是1，上传成功！
                return 1;
            }
            catch (Exception ex)
            {
                //返回如果是-1，上传失败
                return -1;
            }
        }



    }
}
