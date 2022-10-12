# How to use the app 

- 

- [X] Users can now sign into the account using an existing account. 
- [X] Users can now create an account of using a custom username or custom password. 
    - [X] Users can now change their username or password using the in-app password and username change utility tool. 

  
# How do you use the string regex command
<table> 
  <tr> 
    <th> 
      Keyword / Method 
    </th>
    <th> 
      Description
    </th>
    <th> 
      Syntax
    </th>
    <th> 
      Error Statement
    </th>
  </tr>
  <tr> 
    <td> 
      <code>@regexDivision{ }</code>
    </td>
    <td align="justify"> 
      Main entry point into every single regex-Co (regex-conditional) statement.
    </td>
    <td> 
      <code> 
        @regexDivision{  
            &emsp;&emsp;&emsp;!!YourCodeOverHere!!
        }
      </code>
    </td>
    <td> 
      <table> 
        <tr> 
          <th> 
            Error No.
          </th>
          <th> 
            Error Example
          </th>
          <th> 
            Problem
          </th>
          <th> 
            Error Statement
          </th>
        </tr>
        <tr> 
          <td align = "center"> 1 </td>
          <td> 
            <code> @regexDivision </code>
          </td>
          <td> 
            Missing curly braces <code> { } </code> found in the declaration. 
          </td>
          <td> 
            <code> E1a: Expected '{' after @regexDivision </code>
          </td>
        </tr>
        <tr> 
          <td align = "center"> 2 </td>
          <td align= "justify"><code>regexDivision</code></td>
          <td>Absent <code>@</code> in front of reserved keyword in the regex conditional statement. </td>
          <td align = "justify"><code>E1b: Expected '@' in front of regexDivision to complete reserved keyword declaration.</code></td>
        </tr>
        <tr> 
          <td align = "center"> 3 </td>
          <td align = "justify"><code>regexDivi</code></td>
          <td> There is no such keyword found </td>
          <td> </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
